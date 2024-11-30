package dev.arman.userservice.repositories;

import dev.arman.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author mdarmanansari
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    User save(User user);

    Optional<User> findByEmail(String email);
}
