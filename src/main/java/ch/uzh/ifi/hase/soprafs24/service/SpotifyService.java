package ch.uzh.ifi.hase.soprafs24.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;
import java.io.FileInputStream;
import org.springframework.core.io.ClassPathResource;

import ch.uzh.ifi.hase.soprafs24.entity.SongInfo;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

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
        LOGGER.info("Using clientId: {} and clientSecret: {}", clientId, clientSecret);
        if (clientId == null || clientSecret == null) {
            throw new IllegalStateException("Spotify clientId or clientSecret is not set!");
        }
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

    private String normalizeSongName(String name) {
        // Lowercase the name for case-insensitive comparison
        String lowerName = name.toLowerCase();
        // Remove common variant descriptors and additional spaces
        lowerName = lowerName.replaceAll("\\s*-?\\s*(live|remix|acoustic|version|edit|radio)\\b", "")
        .replaceAll("\\s{2,}", " ") // Replace multiple spaces with a single space
        .trim();
        return lowerName;
    }

    public List<SongInfo> searchSong(String market, String genre, String artist, String token) {
        // Returned example: "name, artist, imageUrl, href"
        // returned example with descrption = English, genre = pop, artist = Maroon 5 :
        // [
        // {
        //  "title": "Girls Like You",
        //  "artist": "Maroon 5",
        //  "imageUrl": "https://i.scdn.co/image/ab67616d0000b273c41f2b16c718388133a7e994",
        //  "playUrl": "https://api.spotify.com/v1/tracks/5xzGT3Zg5QDlRqgNDPrumy"
        //},
        //{
        //  "title": "Animals",g
        //  "artist": "Maroon 5",
        //  "imageUrl": "https://i.scdn.co/image/ab67616d0000b2737a2a4b225ad828477e358206",
        //  "playUrl": "https://api.spotify.com/v1/tracks/4H52xXIWHfi68h8VqBcS4V"
        //}
        //]
        String query = String.format("genre:%s artist:%s", genre, artist);

        LOGGER.info("Searching for song with query: {}", query);
        String searchResult = WebClient.create("https://api.spotify.com")
                .get()
                .uri(uriBuilder -> uriBuilder.path("/v1/search")
                        .queryParam("q", query)
                        .queryParam("type", "track") // Only search for tracks
                        .queryParam("limit", 20) // Limit to 10 results because we will filter duplicates
                        .queryParam("market", market) 
                        .build())
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Consider using async handling with Mono instead of block

        LOGGER.info("Received search result");

        List<SongInfo> songsInfo = new ArrayList<>();
        Set<String> normalizedSongNames = new HashSet<>();// Track added song names to filter duplicates: one song can have multiple versions, like remixes, lives...

        try {
            JsonNode searchJson = objectMapper.readTree(searchResult);
            JsonNode items = searchJson.path("tracks").path("items");
            for (JsonNode item : items) {
                // utf-8 encoding for songs' name
                String name = item.path("name").asText();
                String normalized = normalizeSongName(name);
                // filter to ensure only add songs with different names
                if (!normalizedSongNames.contains(normalized)) {
                    String artistName = item.path("artists").get(0).path("name").asText();
                    String imageUrl = item.path("album").path("images").get(0).path("url").asText();
                    String href = item.path("href").asText();
                    songsInfo.add(new SongInfo(name, artistName, imageUrl, href));
                    // print the songs' name, artist name, image url and href
                    LOGGER.info("Song name: {}, Artist name: {}, Image URL: {}, Href: {}", normalized, artistName, imageUrl, href);
                    normalizedSongNames.add(normalized);
                } 
                if (normalizedSongNames.size() >= 2) {
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error parsing search result", e);
            throw new RuntimeException("Error parsing search result", e);
        }

        LOGGER.info("Parsed search results successfully");
        // return the list of songs
        return songsInfo;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    

    // a main method to use above methods
    public static void main(String[] args) {
        try {
            // Manually loading properties
            Properties props = new Properties();
            ClassPathResource resource = new ClassPathResource("application.properties");
            props.load(new FileInputStream(resource.getFile()));
    
            // Create an instance of the service
            SpotifyService spotifyService = new SpotifyService();
            
            // Set properties manually
            spotifyService.setClientId(props.getProperty("spotify.client.id"));
            spotifyService.setClientSecret(props.getProperty("spotify.client.secret"));
    
            // Authenticate and search for songs
            String token = spotifyService.authenticate();
            List<SongInfo> songs = spotifyService.searchSong("US", "pop", "Coldplay", token);
            System.out.println(songs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
