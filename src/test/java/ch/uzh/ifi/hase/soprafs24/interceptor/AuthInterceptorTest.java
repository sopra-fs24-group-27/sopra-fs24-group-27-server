package ch.uzh.ifi.hase.soprafs24.interceptor;

import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs24.security.TokenUtils;
import ch.uzh.ifi.hase.soprafs24.interceptor.AuthInterceptor;
import ch.uzh.ifi.hase.soprafs24.entity.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthInterceptorTest {

    @InjectMocks
    private AuthInterceptor authInterceptor;

    @Mock
    private TokenUtils tokenUtils;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PrintWriter writer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPreHandleOptionsMethod() throws Exception {
        when(request.getMethod()).thenReturn("OPTIONS");

        assertTrue(authInterceptor.preHandle(request, response, new Object()));

        verify(tokenUtils, never()).isEmpty(anyString());
        verify(userRepository, never()).findByToken(anyString());
    }

    @Test
    public void testPreHandleEmptyToken() throws Exception {
        when(request.getMethod()).thenReturn("GET");
        when(request.getHeader("Authorization")).thenReturn(null);
        when(tokenUtils.isEmpty(null)).thenReturn(true);
        when(response.getWriter()).thenReturn(writer);

        assertFalse(authInterceptor.preHandle(request, response, new Object()));

        verify(writer).print("You must log in first.");
        verify(userRepository, never()).findByToken(anyString());
    }

    @Test
    public void testPreHandleInvalidToken() throws Exception {
        when(request.getMethod()).thenReturn("GET");
        when(request.getHeader("Authorization")).thenReturn("invalid-token");
        when(tokenUtils.isEmpty("invalid-token")).thenReturn(false);
        when(userRepository.findByToken("invalid-token")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> authInterceptor.preHandle(request, response, new Object()));

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        assertEquals("Unauthorized", exception.getReason());
    }

    @Test
    public void testPreHandleValidToken() throws Exception {
        when(request.getMethod()).thenReturn("GET");
        when(request.getHeader("Authorization")).thenReturn("valid-token");
        when(tokenUtils.isEmpty("valid-token")).thenReturn(false);
        when(userRepository.findByToken("valid-token")).thenReturn(Optional.of(new User()));

        assertTrue(authInterceptor.preHandle(request, response, new Object()));

        verify(response, never()).getWriter();
    }
}
