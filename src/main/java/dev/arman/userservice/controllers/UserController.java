package dev.arman.userservice.controllers;

import dev.arman.userservice.dtos.LoginRequestDto;
import dev.arman.userservice.dtos.LogoutRequestDto;
import dev.arman.userservice.dtos.SignUpRequestDto;
import dev.arman.userservice.exceptions.PasswordIsIncorrectException;
import dev.arman.userservice.exceptions.TokenNotExistsException;
import dev.arman.userservice.exceptions.UserAlreadyExistsException;
import dev.arman.userservice.exceptions.UserNotExistsException;
import dev.arman.userservice.models.Token;
import dev.arman.userservice.models.User;
import dev.arman.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author mdarmanansari
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException {
        String fullName = signUpRequestDto.getName();
        String email = signUpRequestDto.getEmail();
        String password = signUpRequestDto.getPassword();

        return new ResponseEntity<>(userService.signUp(fullName, email, password), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody LoginRequestDto loginRequestDto) throws UserNotExistsException, PasswordIsIncorrectException {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        return new ResponseEntity<>(userService.login(email, password), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) throws TokenNotExistsException {
        userService.logout(logoutRequestDto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validate/{token}")
    public ResponseEntity<User> validateToken(@PathVariable("token") String token) throws TokenNotExistsException {
        return new ResponseEntity<>(userService.validateToken(token), HttpStatus.OK);
    }
}
