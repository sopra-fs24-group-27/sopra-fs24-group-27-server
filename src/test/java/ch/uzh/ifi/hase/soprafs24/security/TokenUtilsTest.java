package ch.uzh.ifi.hase.soprafs24.security;

import ch.uzh.ifi.hase.soprafs24.security.TokenUtils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

@SpringBootTest
public class TokenUtilsTest {

    @Autowired
    private TokenUtils tokenUtils;

    @Test
    public void testGenerate() {
        String token = tokenUtils.generate();
        assertNotNull(token, "Generated token should not be null");
        assertFalse(token.isEmpty(), "Generated token should not be empty");
        assertDoesNotThrow(() -> UUID.fromString(token), "Generated token should be a valid UUID");
    }

    @Test
    public void testIsEmptyWithNull() {
        assertTrue(tokenUtils.isEmpty(null), "isEmpty should return true for null input");
    }

    @Test
    public void testIsEmptyWithEmptyString() {
        assertTrue(tokenUtils.isEmpty(""), "isEmpty should return true for empty string input");
    }

    @Test
    public void testIsEmptyWithNonEmptyString() {
        assertFalse(tokenUtils.isEmpty("some-token"), "isEmpty should return false for non-empty string input");
    }
}
