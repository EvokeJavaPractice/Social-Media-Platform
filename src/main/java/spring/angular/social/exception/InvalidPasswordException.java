package spring.angular.social.exception;

public class InvalidPasswordException extends RuntimeException {

    private String message;

    public InvalidPasswordException(String msg) {
        super(msg);
        this.message = msg;
    }
}
