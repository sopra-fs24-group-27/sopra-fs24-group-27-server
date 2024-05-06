package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.rest.dto.*;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class GameController {

    private final GameService gameService;

    GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // `POST /games`
    // Create a game or room
    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public GameGetDTO createGame(@RequestBody GamePostDTO gamePostDTO) {
        Game game = gameService.createRoom(gamePostDTO);
        System.out.println("Game created: " + game.toString());
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
    }

    // `GET /games/{gameId}`
    // Get status of the created game or room
    @GetMapping("/games/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO getGame(@PathVariable String gameId) {
        Game game = gameService.getGameStatus(gameId);
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
    }

    // `PUT /games/{gameId}`
    // Update the created game or room
    // @PutMapping("/games/{gameId}")
    // @ResponseStatus(HttpStatus.OK)
    // @ResponseBody
    // public GameGetDTO updateGame(@PathVariable String gameId, @RequestBody GamePostDTO gamePostDTO) {
    //     Game game = gameService.updateRoom(gameId, gamePostDTO);
    //     System.out.println("Game modified: " + game.toString());
    //    return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
    //}

    // `DELETE /games/{gameId}`
    // Delete the created game or room
    @DeleteMapping("/games/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void deleteGame(@PathVariable String gameId) {
        gameService.deleteRoom(gameId);
        System.out.println("Game deleted: " + gameId);
    }

    // `POST /games/{gameId}/join?playerId={playerId}`
    // Create a joined player, including host, in the created game or room
    @PostMapping("/games/{gameId}/join")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public GameGetDTO join(@PathVariable String gameId, @RequestParam(required = true) Long userId) {
        Game game = gameService.joinRoom(gameId, userId);
        System.out.println("Game status updated: " + game.toString());
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
    }

    // "POST /games/{gameId}/quit?playerId={playerId}"
    // Delete the joined player, including host, from the created game or room,
    // if the player is the host, delete the game or room
    @PostMapping("/games/{gameId}/quit")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO quit(@PathVariable String gameId, @RequestParam(required = true) Long playerId) {
        Game game = gameService.quit(gameId, playerId);
        System.out.println("Game status updated: " + game.toString());
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
    }

    // PUT /games/{gameId}
    // Start game, assign song and spy to each player
    // Contiditon: when there are 4 players in the game, the host can start the game
    @PutMapping("/games/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO start(@PathVariable String gameId) {
        Game game = gameService.startGame(gameId);
        System.out.println("Game status updated: " + game.toString());
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
    }

    // `GET /games/{gameId}/listen/{playerId}`
    // Fetch the assigned song for a specific player
    @GetMapping("/games/{gameId}/listen/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PlayerSongInfoDTO listen(@PathVariable String gameId, @PathVariable Long playerId) {
        // First, find the player within the game context
        Player player = gameService.getPlayerById(gameId, playerId);
        if (player == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        }

        // Convert the player's song information to SongInfoDTO
        PlayerSongInfoDTO songInfoDTO = DTOMapper.INSTANCE.convertEntityToPlayerSongInfoDTO(player);
        if (songInfoDTO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Song info not available for the player");
        }
    
        // Return the DTO which includes the song info and spy status
        songInfoDTO.setSpy(player.isSpy()); // Set if the player is the spy
        System.out.println("Player song info: " + songInfoDTO.toString());
        return songInfoDTO;
    }



    // `POST /games/{gameId}/newEmojiRound`
    // Refresh current emoji round counter, assign random turns for each playr
    @PostMapping("/games/{gameId}/newEmojiRound")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO newEmojiRound(@PathVariable String gameId) {
        Game game = gameService.newEmojiRound(gameId);
        System.out.println("Game status updated: " + game.toString());
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
    }

    // `POST /games/{gameId}/emoji?playerId={playerId}`
    // Store sent emojis from each player
    @PostMapping("/games/{gameId}/emoji")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO emoji(@PathVariable String gameId,
            @RequestParam(required = true) Long playerId,
            @RequestBody EmojiPostDTO emojiPostDTO) {
        Game game = gameService.emoji(gameId, playerId, emojiPostDTO);
        System.out.println("Game status updated: " + game.toString());
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
    }

    // `POST /games/{gameId}/vote?playerId={playerId}`
    // Store sent votes from each player, if all players have voted, compute scores
    // for each player
    @PostMapping("/games/{gameId}/vote")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO vote(@PathVariable String gameId,
            @RequestParam(required = true) Long playerId,
            @RequestBody VotePostDTO votePostDTO) {
        Game game = gameService.vote(gameId, playerId, votePostDTO);
        System.out.println("Game status updated: " + game.toString());
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
    }

    // `POST /games/{gameId}/newRound`
    // Flush buffer for previous round, refresh current round counter
    @PostMapping("/games/{gameId}/newRound")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO newRound(@PathVariable String gameId) {
        Game game = gameService.newRound(gameId);
        System.out.println("Game status updated: " + game.toString());
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
    }

}
