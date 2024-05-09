package ch.uzh.ifi.hase.soprafs24.rest.mapper;

import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GameGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.GamePostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.PlayerSongInfoDTO;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically
 * transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g.,
 * UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for
 * creating information (POST).
 */
@Mapper
public interface DTOMapper {

  DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

  @Mapping(source = "username", target = "username")
  @Mapping(source = "password", target = "password")
  @Mapping(source = "birthDate", target = "birthDate")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "avatar", target = "avatar")
  User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "status", target = "status")
  @Mapping(source = "token", target = "token")
  @Mapping(source = "birthDate", target = "birthDate")
  @Mapping(source = "avatar", target = "avatar")
  UserGetDTO convertEntityToUserGetDTO(User user);

  @Mapping(source = "gameId", target = "gameId")
  @Mapping(source = "hostId", target = "hostId")
  @Mapping(source = "settings", target = "settings")
  @Mapping(source = "currentRound", target = "currentRound")
  @Mapping(source = "players", target = "players")
  @Mapping(target = "settings.market", source = "settings.market")
  @Mapping(target = "settings.artist", source = "settings.artist")
  @Mapping(target = "settings.genre", source = "settings.genre")
  Game convertGamePostDTOtoEntity(GamePostDTO gamePostDTO);

  @Mapping(source = "gameId", target = "gameId")
  @Mapping(source = "hostId", target = "hostId")
  @Mapping(source = "currentRound", target = "currentRound")
  @Mapping(source = "settings", target = "settings")
  @Mapping(source = "players", target = "players")
  @Mapping(source = "currentTurn", target = "currentTurn")
  @Mapping(target = "settings.market", source = "settings.market")
  @Mapping(target = "settings.artist", source = "settings.artist")
  @Mapping(target = "settings.genre", source = "settings.genre")
  GameGetDTO convertEntityToGameGetDTO(Game game);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "songInfo.title", target = "songTitle")
  @Mapping(source = "songInfo.artist", target = "songArtist")
  @Mapping(source = "songInfo.imageUrl", target = "imageUrl")
  @Mapping(source = "songInfo.playUrl", target = "playUrl")
  PlayerSongInfoDTO convertEntityToPlayerSongInfoDTO(Player player);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "status", target = "status")
  @Mapping(source = "token", target = "token")
  @Mapping(source = "birthDate", target = "birthDate")
  User convertUserGetDTOtoEntity(UserGetDTO userGetDTO);

}
