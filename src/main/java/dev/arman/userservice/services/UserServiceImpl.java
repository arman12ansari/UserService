package dev.arman.userservice.services;

import dev.arman.userservice.exceptions.PasswordIsIncorrectException;
import dev.arman.userservice.exceptions.TokenNotExistsException;
import dev.arman.userservice.exceptions.UserAlreadyExistsException;
import dev.arman.userservice.exceptions.UserNotExistsException;
import dev.arman.userservice.models.Token;
import dev.arman.userservice.models.User;
import dev.arman.userservice.repositories.TokenRepository;
import dev.arman.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

/**
 * @author mdarmanansari
 */
@Service
public class UserServiceImpl implements UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, TokenRepository tokenRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User signUp(String fullName, String email, String password) throws UserAlreadyExistsException {
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }

        User user = new User();
        user.setName(fullName);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        User savedUser = userRepository.save(user);

        return savedUser;
    }

    @Override
    public Token login(String email, String password) throws UserNotExistsException, PasswordIsIncorrectException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UserNotExistsException("User with email " + email + " does not exist Please Sign Up");
        }

        User user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
            throw new PasswordIsIncorrectException("Password is incorrect");
        }

        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plusDays(30);

        Date expiryDate = java.sql.Date.valueOf(thirtyDaysLater);

        Token token = new Token();
        token.setUser(user);
        token.setExpiryDate(expiryDate);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        Token savedToken = tokenRepository.save(token);

        return savedToken;
    }

    @Override
    public void logout(String token) throws TokenNotExistsException {
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedEquals(token, false);

        if (optionalToken.isEmpty()) {
            throw new TokenNotExistsException("Token does not exist / Session expired");
        }

        Token token1 = optionalToken.get();
        token1.setDeleted(true);

        tokenRepository.save(token1);
    }

    @Override
    public User validateToken(String token) throws TokenNotExistsException {
        Optional<Token> optionalToken = tokenRepository.
                findByValueAndDeletedEqualsAndExpiryDateGreaterThan(token, false, new Date());

        if (optionalToken.isEmpty()) {
            throw new TokenNotExistsException("Token does not exist / Session expired");
        }

        Token token1 = optionalToken.get();

        return token1.getUser();
    }
}
