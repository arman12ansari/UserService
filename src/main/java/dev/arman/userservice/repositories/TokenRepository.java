package dev.arman.userservice.repositories;

import dev.arman.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

/**
 * @author mdarmanansari
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Override
    Token save(Token token);

    Optional<Token> findByValueAndDeletedEquals(String value, boolean deleted);

    Optional<Token> findByValueAndDeletedEqualsAndExpiryDateGreaterThan(String value, boolean deleted, Date expiryDate);
}
