package spring.angular.social.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ProfileNotFoundException extends RuntimeException {

    private String message;

    public ProfileNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}
