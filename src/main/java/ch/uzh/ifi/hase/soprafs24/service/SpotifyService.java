package ch.uzh.ifi.hase.soprafs24.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Random;
import java.util.Properties;
import java.io.FileInputStream;
import java.net.URI;

import ch.uzh.ifi.hase.soprafs24.entity.SongInfo;

@Service
public class SpotifyService {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    private final WebClient authWebClient;
    private final WebClient apiWebClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Token Caching
    private String cachedToken;
    private long tokenExpiryTime = 0;

    public SpotifyService(WebClient.Builder webClientBuilder) {
        this.authWebClient = webClientBuilder.baseUrl("https://accounts.spotify.com").build();
        this.apiWebClient = webClientBuilder.baseUrl("https://api.spotify.com").build();
    }

    public synchronized String authenticate() {
        if (System.currentTimeMillis() < tokenExpiryTime && cachedToken != null) {
            return cachedToken;
        }

        LOGGER.info("Using clientId: {} and clientSecret: {}", clientId, clientSecret);
        String tokenResponse = authWebClient.post()
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
            cachedToken = rootNode.path("access_token").asText();
            int expiresIn = rootNode.path("expires_in").asInt();
            tokenExpiryTime = System.currentTimeMillis() + (expiresIn * 1000);

            System.out.println("Complete Token: " + cachedToken);  // Print the complete token to console

            return cachedToken;
        } catch (Exception e) {
            LOGGER.error("Error parsing token response", e);
            throw new RuntimeException("Error parsing token response", e);
        }
    }

    private String normalizeSongName(String name) {
        String lowerName = name.toLowerCase();
        lowerName = lowerName.replaceAll("\\s*-?\\s*(live|remix|acoustic|version|edit|radio)\\b", "")
                .replaceAll("\\s{2,}", " ")
                .trim();
        return lowerName;
    }

    public List<SongInfo> searchSong(String market, String genre, String artist, String token) {
        String query = String.format("genre:%s artist:%s", genre, artist);
        LOGGER.info("Searching for song with query: {}", query);
        String searchResult = apiWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/search")
                        .queryParam("q", query)
                        .queryParam("type", "track")
                        .queryParam("limit", 20) // get 20 songs
                        .queryParam("market", market)
                        .build())
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        LOGGER.info("Received search result");
        List<SongInfo> songsInfo = new ArrayList<>();
        Set<String> normalizedSongNames = new HashSet<>();
        // randomly select 2 songs among the 20 songs

        try {
            JsonNode searchJson = objectMapper.readTree(searchResult);
            JsonNode items = searchJson.path("tracks").path("items");
            for (JsonNode item : items) {
                String name = item.path("name").asText();
                String normalized = normalizeSongName(name);
                if (!normalizedSongNames.contains(normalized)) {
                    String artistName = item.path("artists").get(0).path("name").asText();
                    String imageUrl = item.path("album").path("images").get(0).path("url").asText();
                    String href = item.path("href").asText();
                    // get the track id from href Href: https://api.spotify.com/v1/tracks/6ECp64rv50XVz93WvxXMGF  
                    String[] hrefParts = href.split("/");
                    String trackId = hrefParts[hrefParts.length - 1];
                    String previewUrl = fetchPreviewUrl(trackId);
                    songsInfo.add(new SongInfo(name, artistName, imageUrl, previewUrl));
                    System.out.println("Song Name: " + name + " Artist Name: " + artistName + " Image URL: " + imageUrl + " Preview URL: " + previewUrl);  // Print the song name, artist name, image URL, and preview URL to console
                    normalizedSongNames.add(normalized);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error parsing search result", e);
            throw new RuntimeException("Error parsing search result", e);
        }
        
        // Randomly select 2 songs from the list
        Collections.shuffle(songsInfo, new Random());
        if (songsInfo.size() > 2) {
            LOGGER.info("2 randomly selected songs: {}", songsInfo.subList(0, 2));
            return songsInfo.subList(0, 2);
        }
        return songsInfo;
    }
    
    // get preview url with track id
    public String fetchPreviewUrl(String trackId) {
        String embedUrl = String.format("https://open.spotify.com/embed/track/%s", trackId);
        LOGGER.debug("Fetching preview URL from: {}", embedUrl);
        try {
            String htmlContent = apiWebClient.get()
                .uri(URI.create(embedUrl))
                .retrieve()
                .bodyToMono(String.class)
                .block();
            LOGGER.debug("HTML Content: {}", htmlContent);

            // Parsing HTML to extract the preview URL from JSON embedded in the script
            Pattern pattern = Pattern.compile("\"audioPreview\":\\{\"url\":\"(.*?)\"\\}");
            Matcher matcher = pattern.matcher(htmlContent);
            if (matcher.find()) {
                String previewUrl = matcher.group(1);
                System.out.println("Preview URL: " + previewUrl);  // Print the preview URL to console
                return previewUrl;
            } else {
                LOGGER.warn("No preview URL found in HTML content for trackId: {}", trackId);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to fetch or parse preview URL for trackId: {}", trackId, e);
        }
        return null;
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
            SpotifyService spotifyService = new SpotifyService(WebClient.builder());
            
            // Set properties manually
            spotifyService.setClientId(props.getProperty("spotify.client.id"));
            spotifyService.setClientSecret(props.getProperty("spotify.client.secret"));
    
            // Authenticate and search for songs
            String token = spotifyService.authenticate();
            List<SongInfo> songs = spotifyService.searchSong("US", "Pop", "Maroon 5", token);
            System.out.println(songs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
