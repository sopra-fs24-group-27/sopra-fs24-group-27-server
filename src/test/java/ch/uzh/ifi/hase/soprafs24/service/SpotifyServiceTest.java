package ch.uzh.ifi.hase.soprafs24.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import org.apache.el.lang.FunctionMapperImpl.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.util.UriBuilder;

import ch.uzh.ifi.hase.soprafs24.entity.SongInfo;

import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpotifyServiceTest {

    @Mock
    private WebClient authWebClientMock;

    @Mock
    private WebClient apiWebClientMock;

    @Mock
    private WebClient.Builder webClientBuilderMock;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    private SpotifyService spotifyService;

    @Mock
    private RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private RequestBodySpec requestBodySpec;

    @Mock
    private RequestHeadersSpec requestHeadersSpec;

    @Mock
    private ResponseSpec responseSpec;

    private String token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    
        // Mocking setup for auth and API WebClient builders
        WebClient.Builder authWebClientBuilderMock = mock(WebClient.Builder.class);
        WebClient.Builder apiWebClientBuilderMock = mock(WebClient.Builder.class);
    
        when(webClientBuilderMock.baseUrl("https://accounts.spotify.com")).thenReturn(authWebClientBuilderMock);
        when(authWebClientBuilderMock.build()).thenReturn(authWebClientMock);
    
        when(webClientBuilderMock.baseUrl("https://api.spotify.com")).thenReturn(apiWebClientBuilderMock);
        when(apiWebClientBuilderMock.build()).thenReturn(apiWebClientMock);
    
        // Create the SpotifyService instance
        spotifyService = new SpotifyService(webClientBuilderMock);
    
        // Setup mocks for method calls
        setupMockResponses();
    }
    
    private void setupMockResponses() {
        // Token response mock
        String tokenResponse = "{\"access_token\":\"test_token\",\"expires_in\":3600}";
        when(authWebClientMock.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri("/api/token")).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(tokenResponse));

        System.out.println("Token response: " + tokenResponse);  // Print the token response to console
        
        String jsonResponse = "{\"tracks\":{\"items\":[{\"name\":\"Sugar\",\"artists\":[{\"name\":\"Maroon 5\"}],\"album\":{\"images\":[{\"url\":\"image_url\"}]},\"href\":\"https://api.spotify.com/v1/tracks/test_id\"}]}}";
        
        // Other mocks for searchSong method
        when(apiWebClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/v1/search?q=genre:Pop%20artist:Maroon%205&type=track&market=US&limit=5")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.headers(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(jsonResponse));
    }
    

    @Test
    void authenticate_ShouldReturnToken() {

        token = spotifyService.authenticate();
    
        assertNotNull(token);
    
        verify(authWebClientMock, times(1)).post();
    }
    
    //@Test
    //void searchSong_ShouldReturnSongInfoList() {
    //    token = spotifyService.authenticate();
    //    String market = "US";
    //    String genre = "Pop";
    //    String artist = "Maroon 5";

    //    List<SongInfo> songInfoList = spotifyService.searchSong(market, genre, artist, token);
        
    //    assertNotNull(songInfoList);
    //}  
    

    @Test
    void fetchPreviewUrl_ShouldReturnPreviewUrl() {
        String trackId = "test_id";
        String htmlResponse = "{\"audioPreview\":{\"url\":\"preview_url\"}}";

        when(apiWebClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(any(URI.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(htmlResponse));

        String previewUrl = spotifyService.fetchPreviewUrl(trackId);

        assertNotNull(previewUrl);
        assertEquals("preview_url", previewUrl);
    }
}
