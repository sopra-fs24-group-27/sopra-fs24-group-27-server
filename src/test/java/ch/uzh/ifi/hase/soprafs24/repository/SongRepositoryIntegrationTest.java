package ch.uzh.ifi.hase.soprafs24.repository;

import ch.uzh.ifi.hase.soprafs24.entity.SongInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@DataJpaTest
public class SongRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SongInfoRepository songInfoRepository;

    @Test
    public void whenFindByTitle_thenReturnSongs() {
        SongInfo song1 = new SongInfo("Shape of You", "Ed Sheeran", "url1", "playUrl1");
        entityManager.persist(song1);
        entityManager.flush();

        List<SongInfo> foundSongs = songInfoRepository.findByTitle("Shape of You");
        assertThat(foundSongs).hasSize(1).extracting(SongInfo::getArtist).containsOnly("Ed Sheeran");
    }

    @Test
    public void whenFindByArtist_thenReturnSongs() {
        SongInfo song1 = new SongInfo("Yesterday", "The Beatles", "url2", "playUrl2");
        entityManager.persist(song1);
        entityManager.flush();

        List<SongInfo> foundSongs = songInfoRepository.findByArtist("The Beatles");
        assertThat(foundSongs).isNotEmpty().extracting(SongInfo::getTitle).contains("Yesterday");
    }

    @Test
    public void whenFindByImageUrl_thenReturnSong() {
        SongInfo song1 = new SongInfo("Numb", "Linkin Park", "url3", "playUrl3");
        entityManager.persist(song1);
        entityManager.flush();

        Optional<SongInfo> foundSong = songInfoRepository.findByImageUrl("url3");
        assertThat(foundSong).isPresent().hasValueSatisfying(song -> {
            assertThat(song.getTitle()).isEqualTo("Numb");
        });
    }

    @Test
    public void whenFindByPlayUrl_thenReturnSong() {
        SongInfo song1 = new SongInfo("Faint", "Linkin Park", "url4", "playUrl4");
        entityManager.persist(song1);
        entityManager.flush();

        Optional<SongInfo> foundSong = songInfoRepository.findByPlayUrl("playUrl4");
        assertThat(foundSong).isPresent().hasValueSatisfying(song -> {
            assertThat(song.getArtist()).isEqualTo("Linkin Park");
        });
    }

    @Test
    public void whenFindByArtistAndTitle_thenReturnSongs() {
        SongInfo song1 = new SongInfo("In the End", "Linkin Park", "url5", "playUrl5");
        entityManager.persist(song1);
        entityManager.flush();

        List<SongInfo> foundSongs = songInfoRepository.findByArtistAndTitle("Linkin Park", "In the End");
        assertThat(foundSongs).isNotEmpty().extracting(SongInfo::getImageUrl).contains("url5");
    }
}