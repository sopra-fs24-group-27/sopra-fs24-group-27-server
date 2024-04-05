package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.entity.SongInfo;
import ch.uzh.ifi.hase.soprafs24.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyController {

    @Autowired
    private SpotifyService spotifyService;

    @GetMapping("/search")
    public ResponseEntity<List<SongInfo>> searchSong(@RequestParam String description, @RequestParam String genre, @RequestParam String artist) {
        return Mono.fromCallable(() -> spotifyService.authenticate()) // Wrap the synchronous call
                .flatMap(token -> Mono.fromCallable(() -> spotifyService.searchSong(description, genre, artist, token))) // Wrap the search call as well
                .map(ResponseEntity::ok) // Wrap search results in a ResponseEntity
                .onErrorReturn(ResponseEntity.status(500).build()) // Return a 500 status code on error
                .block(); // Block until the Mono completes
    }
}