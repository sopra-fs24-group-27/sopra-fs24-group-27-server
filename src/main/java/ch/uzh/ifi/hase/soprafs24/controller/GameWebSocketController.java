package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.entity.Settings;
import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.*;
import ch.uzh.ifi.hase.soprafs24.websocket.mapper.DTOMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.Header;


@Transactional
@Controller
public class GameWebSocketController {

    @Autowired
    private GameService gameService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    // populate the game response object and broadcast it to all subscribers of the game
    private void broadcast(String gameId, GameResponseDTO gameResponse) {
        simpMessagingTemplate.convertAndSend("/topic/games/" + gameId, gameResponse);
        System.out.println("Broadcasting to /topic/games/" + gameId);
    }
    
    private void broadcastError(String gameId, String message) {
        simpMessagingTemplate.convertAndSend("/topic/games/" + gameId + "/errors", message);
        System.out.println("Error broadcast to /topic/games/" + gameId + "/errors");
    }


    @MessageMapping("/games/{gameId}/join")
    public void joinWaitingRoom(@DestinationVariable String gameId, @Payload JoinRoomPayloadDTO payload) {
        try {
            Game game = gameService.joinRoom(gameId, payload.getUserId());
            GameResponseDTO gameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(game);
            broadcast(gameId + "/waitingroom", gameResponse);
        } catch (Exception e) {
            broadcastError(gameId, "Join failed: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Join failed", e);
        }
    }
    
    @MessageMapping("/games/{gameId}/start")
    public void startGame(@DestinationVariable String gameId, @Header("userId") Long userId) {
        if (!gameService.isHost(userId, gameId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only the host can start the game");
        }
        
        Game gameStatus = gameService.startGame(gameId);
        GameResponseDTO generalGameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(gameStatus);
        broadcast(gameId + "/start", generalGameResponse);  // Make sure all needed info is sent
    
        gameStatus.getPlayers().forEach(player -> {
            PlayerSongInfoDTO songInfoDTO = DTOMapper.INSTANCE.convertEntityToPlayerSongInfoDTO(player);
            simpMessagingTemplate.convertAndSendToUser(
                player.getUser().getId().toString(),
                "/queue/listen",  // Direct message to user queue for song info
                songInfoDTO
            );
        });
    }
    

    @MessageMapping("/games/{gameId}/sortTurnOrder")
    public void sortTurnOrder(@DestinationVariable String gameId) {
        Game game = gameService.sortTurnOrder(gameId);
        GameResponseDTO gameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(game);
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
