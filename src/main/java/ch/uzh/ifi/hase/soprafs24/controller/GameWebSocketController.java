package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.entity.Settings;
import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.GameResponseDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.JoinRoomPayloadDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.ModifySettingsPayloadDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.SendEmojisPayloadDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.LeaveRoomPayloadDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.mapper.DTOMapper;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.stereotype.Controller;

public class GameWebSocketController {

    private final GameService gameService;

    GameWebSocketController(GameService gameService) {
        this.gameService = gameService;
    }

    private SimpMessagingTemplate simpMessagingTemplate;

    private void broadcast(String gameId, GameResponseDTO gameResponse) {
        // Use SimpMessagingTemplate to send to a specific topic
        simpMessagingTemplate.convertAndSend("/topic/games/" + gameId, gameResponse);
    }

    @MessageMapping("/games/{gameId}/joinRoom")
    public void joinRoom(@DestinationVariable String gameId, @Payload JoinRoomPayloadDTO joinRoomPayloadDTO) {
        // Process the player joining logic here
        Player playerInput = DTOMapper.INSTANCE.convertJoinRoomPayloadDTOtoEntity(joinRoomPayloadDTO);

        Game gameStatus = gameService.joinRoom(gameId, playerInput);

        GameResponseDTO gameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(gameStatus);

        // Then broadcast the new player's arrival to all subscribers of this room
        broadcast(gameId, gameResponse);
    }

    @MessageMapping("/games/{gameId}/modifySettings")
    public void modifySettings(@DestinationVariable String gameId,
            @Payload ModifySettingsPayloadDTO modifySettingsPayloadDTO) {
        // Process the player joining logic here
        Settings settingsInput = DTOMapper.INSTANCE.convertModifySettingsPayloadDTOtoEntity(modifySettingsPayloadDTO);

        Game gameStatus = gameService.modifySettings(gameId, settingsInput);

        GameResponseDTO gameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(gameStatus);

        // Then broadcast the new player's arrival to all subscribers of this room
        broadcast(gameId, gameResponse);
    }

    @MessageMapping("/games/{gameId}/startRound")
    public void startRound(@DestinationVariable String gameId) {
        // Process the round starting, i.e., player role assigning, logic here
        Game gameStatus = gameService.startRound(gameId);

        GameResponseDTO gameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(gameStatus);

        // Then broadcast the new player's arrival to all subscribers of this room
        broadcast(gameId, gameResponse);
    }

    @MessageMapping("/games/{gameId}/sortTurnOrder")
    public void sortTurnOrder(@DestinationVariable String gameId) {
        // Process the player turn order sorting logic here
        Game gameStatus = gameService.sortTurnOrder(gameId);

        GameResponseDTO gameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(gameStatus);

        // Then broadcast the new player's arrival to all subscribers of this room
        broadcast(gameId, gameResponse);
    }

    @MessageMapping("/games/{gameId}/sendEmojis")
    public void sendEmojis(@DestinationVariable String gameId, @Payload SendEmojisPayloadDTO sendEmojisPayloadDTO) {
        // Process the emojis sending logic here
        Player playerInput = DTOMapper.INSTANCE.convertSendEmojisPayloadDTOtoEntity(sendEmojisPayloadDTO);

        Game gameStatus = gameService.sendEmojis(gameId, playerInput);

        GameResponseDTO gameResponse = DTOMapper.INSTANCE.convertEntityToGameResponseDTO(gameStatus);

        // Then broadcast the new player's arrival to all subscribers of this room
        broadcast(gameId, gameResponse);
    }

    // Who voted matters as well!
    @MessageMapping("/games/{gameId}/vote")
    public void vote(@DestinationVariable String gameId, @Payload Player player) {
        // Process the player voting logic here
        Game gameStatus = gameService.vote(gameId, player);

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
