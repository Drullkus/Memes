package us.drullk.memes.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import us.drullk.memes.database.entity.UserData;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, Long> {
    // TODO Any additional functionality: https://www.baeldung.com/spring-data-jpa-method-in-all-repositories

    Optional<UserData> findByDisplayName(@Param("name") String name);
}
