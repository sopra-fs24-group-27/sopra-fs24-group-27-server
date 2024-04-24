package ch.uzh.ifi.hase.soprafs24.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ch.uzh.ifi.hase.soprafs24.entity.Game;

@DataJpaTest
public class GameRepositoryIntegrationTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void testFindByGameId() {
        // Setup data scenario
        Game game = new Game();
        game.setGameId("testGameId");
        game = gameRepository.save(game);

        // Test findByGameId
        Optional<Game> foundGame = gameRepository.findByGameIdWithPlayers(game.getGameId());
        assertThat(foundGame).isPresent();
        assertThat(foundGame.get().getGameId()).isEqualTo(game.getGameId());
    }
}
