package ch.uzh.ifi.hase.soprafs24.websocket.mapper;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.entity.Settings;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.GameResponseDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.JoinRoomPayloadDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.ModifySettingsPayloadDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.SendEmojisPayloadDTO;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.LeaveRoomPayloadDTO;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    @Mapping(source = "id", target = "id")
    Player convertJoinRoomPayloadDTOtoEntity(JoinRoomPayloadDTO joinRoomPayloadDTO);

    @Mapping(source = "language", target = "language")
    @Mapping(source = "style", target = "style")
    @Mapping(source = "artist", target = "artist")
    Settings convertModifySettingsPayloadDTOtoEntity(ModifySettingsPayloadDTO modifySettingsPayloadDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "emojis", target = "emojis")
    Player convertSendEmojisPayloadDTOtoEntity(SendEmojisPayloadDTO sendEmojisPayloadDTO);

    @Mapping(source = "id", target = "id")
    Player convertLeaveRoomPayloadDTOtoEntity(LeaveRoomPayloadDTO leaveRoomPayloadDTO);

    @Mapping(source = "gameId", target = "gameId")
    @Mapping(source = "hostId", target = "hostId")
    @Mapping(source = "currentRound", target = "currentRound")
    @Mapping(source = "settings", target = "settings")
    @Mapping(source = "players", target = "players")
    GameResponseDTO convertEntityToGameResponseDTO(Game game);
}
