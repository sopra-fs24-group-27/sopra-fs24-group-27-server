package ch.uzh.ifi.hase.soprafs24.repository;

import ch.uzh.ifi.hase.soprafs24.entity.Game;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("gameRepository")
public interface GameRepository extends JpaRepository<Game, Long> {
    // This method should fetch the game along with its players eagerly
    @Query("SELECT g FROM Game g LEFT JOIN FETCH g.players WHERE g.gameId = :gameId")
    Optional<Game> findByGameIdWithPlayers(String gameId);
}
