package spring.angular.social.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MessageNotFoundException extends RuntimeException {

    private final String message;
    public MessageNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}
