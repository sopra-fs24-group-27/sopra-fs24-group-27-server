package ch.uzh.ifi.hase.soprafs24.repository;

import ch.uzh.ifi.hase.soprafs24.entity.SongInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongInfoRepository extends JpaRepository<SongInfo, Long> {
    // Find a list of SongInfo by title. There could be multiple songs with the same title.
    List<SongInfo> findByTitle(String title);

    // Find a list of SongInfo by artist. One artist could have many songs.
    List<SongInfo> findByArtist(String artist);

    // Optional in case there's no matching URL or to ensure uniqueness if that's a requirement.
    Optional<SongInfo> findByImageUrl(String imageUrl);

    Optional<SongInfo> findByPlayUrl(String playUrl);

    // Find songs by a combination of artist and title.
    List<SongInfo> findByArtistAndTitle(String artist, String title);
}
