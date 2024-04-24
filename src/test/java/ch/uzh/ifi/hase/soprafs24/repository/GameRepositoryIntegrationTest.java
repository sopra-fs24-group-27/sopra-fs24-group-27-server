package ch.uzh.ifi.hase.soprafs24.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;

@DataJpaTest
public class GameRepositoryIntegrationTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    @Transactional
    public void testFindByGameId() {
        // Setup data scenario
        Game game = new Game();
        game.setGameId("testGameId");
        
        Player player = new Player();
        player.setGame(game);
        game.getPlayers().add(player);
    
        game = gameRepository.saveAndFlush(game);  // Use saveAndFlush to immediately commit to the database
    
        // Test findByGameId
        Optional<Game> foundGame = gameRepository.findByGameIdWithPlayers(game.getGameId());
        assertThat(foundGame).isPresent();
        assertThat(foundGame.get().getGameId()).isEqualTo(game.getGameId());
        assertThat(foundGame.get().getPlayers()).hasSize(1); // Verify that players are fetched correctly
    }
    
    
}
