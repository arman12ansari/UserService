package dev.arman.userservice.exceptions;

/**
 * @author mdarmanansari
 */
public class PasswordIsIncorrectException extends Exception {
    public PasswordIsIncorrectException() {
        super();
    }

    public PasswordIsIncorrectException(String message) {
        super(message);
    }

    public PasswordIsIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordIsIncorrectException(Throwable cause) {
        super(cause);
    }

    protected PasswordIsIncorrectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
