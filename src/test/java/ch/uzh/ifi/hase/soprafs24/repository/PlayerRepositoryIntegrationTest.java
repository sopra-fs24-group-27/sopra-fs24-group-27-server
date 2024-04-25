package ch.uzh.ifi.hase.soprafs24.repository;

import ch.uzh.ifi.hase.soprafs24.entity.Game;
import ch.uzh.ifi.hase.soprafs24.entity.Player;
import ch.uzh.ifi.hase.soprafs24.entity.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PlayerRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void testFindByGameId() {
        // Arrange
        Game game = new Game();
        game.setGameId("some_unique_game_id");
        entityManager.persist(game);

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        entityManager.persist(user);

        Player player = new Player();
        player.setGame(game);
        player.setUser(user);
        entityManager.persist(player);
        entityManager.flush();

        // Act
        Optional<Player> found = playerRepository.findByGameId(game.getId());

        // Assert
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getGame().getId()).isEqualTo(game.getId());
    }

    @Test
    public void testFindByUserId() {
        // Arrange
        User user = new User();
        user.setUsername("testuser2");
        user.setPassword("password2");
        entityManager.persist(user);

        Game game = new Game();
        game.setGameId("some_unique_game_id");
        entityManager.persist(game);

        Player player = new Player();
        player.setUser(user);
        player.setGame(game);
        entityManager.persist(player);
        entityManager.flush();

        // Act
        Optional<Player> found = playerRepository.findByUserId(user.getId());

        // Assert
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getUser().getId()).isEqualTo(user.getId());
    }
}