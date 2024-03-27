package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyController {

    @Autowired
    private SpotifyService spotifyService;

    @GetMapping("/search")
    public Mono<ResponseEntity<String>> searchSong(@RequestParam String query, @RequestParam String type) {
        return Mono.fromCallable(() -> spotifyService.authenticate()) // Wrap the synchronous call
                .flatMap(token -> Mono.fromCallable(() -> spotifyService.searchSong(query, type, token))) // Wrap the search call as well
                .map(ResponseEntity::ok) // Wrap search results in a ResponseEntity
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body("Error during the Spotify search: " + e.getMessage()))); // Error handling
    }
}
