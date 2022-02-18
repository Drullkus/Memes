package us.drullk.memes.database;

import org.springframework.data.jpa.repository.JpaRepository;
import us.drullk.memes.database.entity.Memer;

public interface UserRepository extends JpaRepository<Memer, Long> {
    // TODO Investigate further on designing getters for a database.
    //  There seems to be a helpful "Plain old Java" way of doing designing these.
}
