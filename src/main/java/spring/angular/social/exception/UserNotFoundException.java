package spring.angular.social.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotFoundException extends RuntimeException {

    private String message;

    public UserNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}
