package ch.uzh.ifi.hase.soprafs24.websocket.mapper;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.GameResponseDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.JoinRoomPayloadDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.SendEmojisPayloadDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.LeaveRoomPayloadDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.PlayerInfoDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.PlayerSongInfoDTO;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    @Mapping(source = "userId", target = "user.id")
    Player convertJoinRoomPayloadDTOtoEntity(JoinRoomPayloadDTO joinRoomPayloadDTO);

    @Mapping(source = "id", target = "id")
    @Mapping (source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.avatar", target = "avatar")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "emojis", target = "emojis")
    @Mapping(source = "turn", target = "turn")
    PlayerInfoDTO convertEntityToPlayerInfoDTO(Player player);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "songInfo.title", target = "songTitle")
    @Mapping(source = "songInfo.artist", target = "songArtist")
    @Mapping(source = "songInfo.playUrl", target = "playUrl")
    @Mapping(source = "songInfo.imageUrl", target = "imageUrl")
    PlayerSongInfoDTO convertEntityToPlayerSongInfoDTO(Player player);

    @Mapping(source = "playerId", target = "id")
    @Mapping(source = "emojis", target = "emojis")
    Player convertSendEmojisPayloadDTOtoEntity(SendEmojisPayloadDTO sendEmojisPayloadDTO);

    @Mapping(source = "userId", target = "user.id")
    Player convertLeaveRoomPayloadDTOtoEntity(LeaveRoomPayloadDTO leaveRoomPayloadDTO);

    @Mapping(source = "gameId", target = "gameId")
    @Mapping(source = "hostId", target = "hostId")
    @Mapping(source = "currentRound", target = "currentRound")
    @Mapping(source = "settings", target = "settings")
    @Mapping(source = "players", target = "players")
    GameResponseDTO convertEntityToGameResponseDTO(Game game);

}
