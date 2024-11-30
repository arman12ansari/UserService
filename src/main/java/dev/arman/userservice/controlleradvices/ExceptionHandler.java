package dev.arman.userservice.controlleradvices;

import dev.arman.userservice.dtos.ExceptionDto;
import dev.arman.userservice.exceptions.PasswordIsIncorrectException;
import dev.arman.userservice.exceptions.TokenNotExistsException;
import dev.arman.userservice.exceptions.UserAlreadyExistsException;
import dev.arman.userservice.exceptions.UserNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author mdarmanansari
 */
@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionDto> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(exception.getMessage());
        exceptionDto.setDetails("User already exists");
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<ExceptionDto> handleUserNotExistsException(UserNotExistsException exception) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(exception.getMessage());
        exceptionDto.setDetails("User not found");
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(PasswordIsIncorrectException.class)
    public ResponseEntity<ExceptionDto> handlePasswordIsIncorrectException(PasswordIsIncorrectException exception) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(exception.getMessage());
        exceptionDto.setDetails("Password is incorrect");
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TokenNotExistsException.class)
    public ResponseEntity<ExceptionDto> handleTokenNotExistsException(TokenNotExistsException exception) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(exception.getMessage());
        exceptionDto.setDetails(exception.getLocalizedMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }
}
