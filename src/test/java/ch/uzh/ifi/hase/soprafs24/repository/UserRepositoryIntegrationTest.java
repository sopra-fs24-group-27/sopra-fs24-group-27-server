package ch.uzh.ifi.hase.soprafs24.repository;

import ch.uzh.ifi.hase.soprafs24.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsername_success() {
        // Arrange
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setPassword("password");
        user.setStatus(UserStatus.ONLINE);
        user.setToken("1");
        user.setBirthDate(null);
        user.setAvatar("avatar.png"); // Set the avatar field

        entityManager.persist(user);
        entityManager.flush();

        // Act
        User found = userRepository.findByUsername(user.getUsername());

        // Assert
        assertNotNull(found.getId());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getPassword(), user.getPassword());
        assertEquals(found.getToken(), user.getToken());
        assertEquals(found.getStatus(), user.getStatus());
        assertEquals(found.getBirthDate(), user.getBirthDate());
        assertEquals(found.getAvatar(), user.getAvatar()); // Verify the avatar field
    }

    @Test
    public void findByToken_success() {
        // Arrange
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setPassword("password");
        user.setStatus(UserStatus.ONLINE);
        user.setToken("1");
        user.setBirthDate(null);
        user.setAvatar("avatar.png"); // Set the avatar field

        entityManager.persist(user);
        entityManager.flush();

        // Act
        Optional<User> foundOptional = userRepository.findByToken(user.getToken());

        // Assert
        assertTrue(foundOptional.isPresent(), "User must be present"); // Check if the user is present in the Optional

        // Extract user if present
        User found = foundOptional.get();
        assertNotNull(found.getId());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getPassword(), user.getPassword());
        assertEquals(found.getToken(), user.getToken());
        assertEquals(found.getStatus(), user.getStatus());
        assertEquals(found.getBirthDate(), user.getBirthDate());
        assertEquals(found.getAvatar(), user.getAvatar()); // Verify the avatar field
    }
}
