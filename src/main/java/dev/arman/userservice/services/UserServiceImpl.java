package dev.arman.userservice.services;

import dev.arman.userservice.exceptions.PasswordIsIncorrectException;
import dev.arman.userservice.exceptions.TokenNotExistsException;
import dev.arman.userservice.exceptions.UserAlreadyExistsException;
import dev.arman.userservice.exceptions.UserNotExistsException;
import dev.arman.userservice.models.Token;
import dev.arman.userservice.models.User;
import dev.arman.userservice.repositories.TokenRepository;
import dev.arman.userservice.repositories.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

/**
 * @author mdarmanansari
 */
@Service
public class UserServiceImpl implements UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    @Value("${app.secret.key}")
    private String secretKey;

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
    public String login(String email, String password) throws UserNotExistsException, PasswordIsIncorrectException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UserNotExistsException("User with email " + email + " does not exist Please Sign Up");
        }

        User user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
            throw new PasswordIsIncorrectException("Password is incorrect");
        }
/*
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plusDays(30);

        Date expiryDate = java.sql.Date.valueOf(thirtyDaysLater);

        To generate token by random value and insert into database

        Token token = new Token();
        token.setUser(user);
        token.setExpiryDate(expiryDate);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        Token savedToken = tokenRepository.save(token);

        return savedToken;

 */
        String jwtToken = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuer("dev.arman.userservice")
                .claim("name", user.getName())
                .claim("role", "user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                .signWith(HS256, secretKey) // Use a secure key in production
                .compact();

        return jwtToken;
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
        /* To validate token from database
        Optional<Token> optionalToken = tokenRepository.
                findByValueAndDeletedEqualsAndExpiryDateGreaterThan(token, false, new Date());

        if (optionalToken.isEmpty()) {
            throw new TokenNotExistsException("Token does not exist / Session expired");
        }

        Token token1 = optionalToken.get();

        return token1.getUser();
         */
        User user = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)   // Use the same secret key as when signing the JWT
                    .parseClaimsJws(token)      // Parse the token (verifies signature and expiration)
                    .getBody();

            Date expiryDate = claims.getExpiration();
            if (expiryDate.before(new Date())) {
                throw new TokenNotExistsException("Token expired");
            }

            String email = claims.getSubject();
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isEmpty()) {
                throw new TokenNotExistsException("User not found");
            }
            user = optionalUser.get();
        } catch (ExpiredJwtException e) {
            throw new TokenNotExistsException(e.getMessage());
        } catch (SignatureException e) {
            throw new TokenNotExistsException(e.getMessage());
        } catch (MalformedJwtException e) {
            throw new TokenNotExistsException(e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new TokenNotExistsException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new TokenNotExistsException(e.getMessage());
        }
        return user;
    }
}
