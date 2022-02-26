package us.drullk.memes.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import us.drullk.memes.database.entity.Memer;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Memer, Long> {
    // TODO Any additional functionality: https://www.baeldung.com/spring-data-jpa-method-in-all-repositories

    Optional<Memer> findByDisplayName(@Param("name") String name);
}
