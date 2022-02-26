package us.drullk.memes.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import us.drullk.memes.database.entity.Meme;

import java.util.List;

public interface MemeRepository extends JpaRepository<Meme, Long> {
    // TODO Any additional functionality: https://www.baeldung.com/spring-data-jpa-method-in-all-repositories

    List<Meme> findByPoster(@Param("poster") long poster);
}
