package spring.angular.social.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidPasswordException extends RuntimeException {

    private String message;

    public InvalidPasswordException(String msg) {
        super(msg);
        this.message = msg;
    }
}
