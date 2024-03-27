package ch.uzh.ifi.hase.soprafs24.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SpotifyService {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    private WebClient webClient = WebClient.create("https://accounts.spotify.com");

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String authenticate() {
        String tokenResponse = webClient.post()
                .uri("/api/token")
                .headers(headers -> {
                    headers.setBasicAuth(clientId, clientSecret);
                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                })
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Consider using async handling with Mono instead of block

        LOGGER.info("Received token response");
        try {
            JsonNode rootNode = objectMapper.readTree(tokenResponse);
            String token = rootNode.path("access_token").asText();
            return token;
        } catch (Exception e) {
            LOGGER.error("Error parsing token response", e);
            throw new RuntimeException("Error parsing token response", e);
        }
    }

    public String searchSong(String query, String type, String token) {
        LOGGER.info("Searching for song with query: {}", query);
        String searchResult = WebClient.create("https://api.spotify.com/v1")
                .get()
                .uri(uriBuilder -> uriBuilder.path("/search")
                        .queryParam("q", query)
                        .queryParam("type", type)
                        .queryParam("limit", 2) // Adjust based on how many results you want
                        .build())
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Consider using async handling with Mono instead of block

        LOGGER.info("Received search result");
        return searchResult;
    }
}
