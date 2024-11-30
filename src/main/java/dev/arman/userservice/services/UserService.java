package dev.arman.userservice.services;

import dev.arman.userservice.exceptions.PasswordIsIncorrectException;
import dev.arman.userservice.exceptions.TokenNotExistsException;
import dev.arman.userservice.exceptions.UserAlreadyExistsException;
import dev.arman.userservice.exceptions.UserNotExistsException;
import dev.arman.userservice.models.User;

/**
 * @author mdarmanansari
 */
public interface UserService {
    public User signUp(String fullName, String email, String password) throws UserAlreadyExistsException;

    public String login(String email, String password) throws UserNotExistsException, PasswordIsIncorrectException;

    public void logout(String token) throws TokenNotExistsException;

    public User validateToken(String token) throws TokenNotExistsException;
}
