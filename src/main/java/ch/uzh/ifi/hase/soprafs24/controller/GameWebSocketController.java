package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.service.SessionService;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.entity.Settings;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.*;
import ch.uzh.ifi.hase.soprafs24.websocket.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.Header;


@Transactional
@Controller
public class GameWebSocketController {

    @Autowired
    private GameService gameService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    // populate the game response object and broadcast it to all subscribers of the game
    private void broadcast(String gameId, GameResponseDTO gameResponse) {
        simpMessagingTemplate.convertAndSend("/topic/games/" + gameId, gameResponse);
        System.out.println("Broadcasting to /topic/games/" + gameId);
    }
    
    private void broadcastError(String gameId, String error) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", error);
        simpMessagingTemplate.convertAndSend("/topic/games/" + gameId + "/errors", errorResponse);
        System.out.println("Error broadcast to /topic/games/" + gameId + "/errors: " + error);
    }
    

    @MessageMapping("/games/{gameId}/join")
    public void joinWaitingRoom(@DestinationVariable String gameId, @Payload JoinRoomPayloadDTO payload) {
        try {
            Game game = gameService.joinRoom(gameId, payload.getUserId());
            GameResponseDTO gameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(game);
            broadcast(gameId + "/waitingroom", gameResponse);
            // broadcast again after 1 second to ensure that the player is added to the game
            Thread.sleep(1000);
            broadcast(gameId + "/waitingroom", gameResponse);
        } catch (Exception e) {
            broadcastError(gameId, "Join failed: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Join failed", e);
        }
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        SimpMessageHeaderAccessor sha = SimpMessageHeaderAccessor.wrap(event.getMessage());
        // Access principal
        Principal principal = sha.getUser(); // This retrieves the principal set during the handshake
        if (principal != null) {
            String sessionId = principal.getName();
            System.out.println("Received a new web socket connection: " + sessionId);
        }
    }
    
    @MessageMapping("/games/{gameId}/start")
    public void startGame(@DestinationVariable String gameId, Principal principal) {
        try {
            Game gameStatus = gameService.startGame(gameId);
            String sessionId = principal.getName();
            GameResponseDTO generalGameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(gameStatus);
            broadcast(gameId + "/start", generalGameResponse);  // Make sure all needed info is sent
            //print the general game response
            System.out.println("General Game Response: " + generalGameResponse);
            System.out.println("Session ID: " + sessionId);
            gameStatus.getPlayers().forEach(player -> {
                PlayerSongInfoDTO songInfoDTO = DTOMapper.INSTANCE.convertEntityToPlayerSongInfoDTO(player);
                // print the player song info
                sessionService.registerPlayerSession(sessionId, player.getUser().getId());
                System.out.println("Player Song Info: " + songInfoDTO);
                simpMessagingTemplate.convertAndSendToUser(
                    sessionId,
                    "/topic/games/" + gameId + "/listen",
                    songInfoDTO
                );
                System.out.println("Sent song info to user " + player.getUser().getId());
                // send again after 1 second to ensure that the player is added to the game
                // Schedule a repeat send after 1 second
                new Thread(() -> {
                try {
                    Thread.sleep(2000); // wait for 2 seconds
                    // first broadcast the game response dto to all players to ensure that the player is added to the game
                    broadcast(gameId, generalGameResponse);
                    // then fetch the players from the gameresponse and get the player with the current user id
                    gameStatus.getPlayers().forEach(player1 -> {
                        if (player1.getUser().getId().equals(player.getUser().getId())) {
                            // convert the player to a player song info dto
                            PlayerSongInfoDTO songInfoDTO1 = DTOMapper.INSTANCE.convertEntityToPlayerSongInfoDTO(player1);
                            // send the song info to the player
                            System.out.println("Re-sending song info to user " + player1.getUser().getId());
                            simpMessagingTemplate.convertAndSendToUser(
                                sessionId,
                                "/topic/games/" + gameId + "/listen",
                                songInfoDTO1
                            );
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                }).start();
            });
        } catch (Exception e) {
            broadcastError(gameId, "Start failed: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Start failed", e);
        }
    }



    @MessageMapping("/games/{gameId}/currentSong")
    @SendToUser("/queue/currentSong")
    public PlayerSongInfoDTO getCurrentSong(@DestinationVariable String gameId, Principal principal) {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
    
        // use userId to get player
        Long userId = Long.valueOf(principal.getName());
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    
        Player player = playerRepository.findByUserId(user.getId())
                                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
        return gameService.getCurrentSongInfo(player.getId());
    }


    @MessageMapping("/games/{gameId}/sortTurnOrder")
    public void sortTurnOrder(@DestinationVariable String gameId) {
        Game game = gameService.sortTurnOrder(gameId);
        GameResponseDTO gameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(game);
        broadcast(gameId + "/round", gameResponse);
        broadcast(gameId, gameResponse);
    }


    @MessageMapping("/games/{gameId}/sendEmojis")
    public void sendEmojis(@DestinationVariable String gameId, @Payload SendEmojisPayloadDTO payload) {
        Game game = gameService.sendEmojis(payload.getPlayerId(), payload.getEmojis());
        GameResponseDTO gameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(game);
        broadcast(gameId, gameResponse);
    }


    @MessageMapping("/games/{gameId}/viewEmojis")
    public void viewEmojis(@DestinationVariable String gameId) {
        // Call the service to get the emoji data for this game
        Map<String, List<String>> emojis = gameService.viewEmojis(Long.valueOf(gameId));

        // Construct a response DTO to send back the emojis
        ViewEmojisResponseDTO emojisResponse = new ViewEmojisResponseDTO();
        emojisResponse.setEmojis(emojis);

        // Broadcast the emoji data to all subscribers of this game's topic
        simpMessagingTemplate.convertAndSend("/topic/games/" + gameId + "/emojis", emojisResponse);
    }

    @MessageMapping("/games/{gameId}/startNewRound")
    public void startNewEmojisRound(@DestinationVariable String gameId) {
        Game game = gameService.startNewEmojisRound(gameId);
        // Notify all players about the new round
        GameResponseDTO response = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(game);
        broadcast(gameId, response);
    }


    // Who voted matters as well!
    @MessageMapping("/games/{gameId}/vote")
    public void vote(@DestinationVariable String gameId, @Payload Player player) {
        // Process the player voting logic here
        Game gameStatus = gameService.vote(gameId, player.getId());

        GameResponseDTO gameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(gameStatus);

        // Then broadcast the new player's arrival to all subscribers of this room
        broadcast(gameId, gameResponse);
    }

    @MessageMapping("/games/{gameId}/endVoting")
    public void endVoting(@DestinationVariable String gameId) {
        // Process the voting ending, i.e., voting result displaying, logic here
        Game gameStatus = gameService.endVoting(gameId);

        GameResponseDTO gameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(gameStatus);

        // Then broadcast the new player's arrival to all subscribers of this room
        broadcast(gameId, gameResponse);
    }

    @MessageMapping("/games/{gameId}/declareWinner")
    public void declareWinner(@DestinationVariable String gameId) {
        // Process the winner declaration logic here
        Game gameStatus = gameService.declareWinner(gameId);

        GameResponseDTO gameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(gameStatus);

        // Then broadcast the new player's arrival to all subscribers of this room
        broadcast(gameId, gameResponse);
    }

    @MessageMapping("/games/{gameId}/endRound")
    public void endRound(@DestinationVariable String gameId) {
        // Process the round ending, i.e., player score updating, logic here
        Game gameStatus = gameService.endRound(gameId);

        GameResponseDTO gameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(gameStatus);

        // Then broadcast the new player's arrival to all subscribers of this room
        broadcast(gameId, gameResponse);
    }

    @MessageMapping("/games/{gameId}/leaveRoom")
    public void leaveRoom(@DestinationVariable String gameId, @Payload LeaveRoomPayloadDTO leaveRoomPayloadDTO) {
        // Process the player (except host) leaving logic here
        Player playerInput = DTOMapper.INSTANCE.convertLeaveRoomPayloadDTOtoEntity(leaveRoomPayloadDTO);

        Game gameStatus = gameService.leaveRoom(gameId, playerInput);

        GameResponseDTO gameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(gameStatus);

        // Then broadcast the new player's arrival to all subscribers of this room
        broadcast(gameId, gameResponse);
    }

}