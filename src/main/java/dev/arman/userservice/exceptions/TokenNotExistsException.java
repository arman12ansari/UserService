package dev.arman.userservice.exceptions;

/**
 * @author mdarmanansari
 */
public class TokenNotExistsException extends Exception {
    public TokenNotExistsException() {
        super();
    }

    public TokenNotExistsException(String message) {
        super(message);
    }

    public TokenNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenNotExistsException(Throwable cause) {
        super(cause);
    }

    protected TokenNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
