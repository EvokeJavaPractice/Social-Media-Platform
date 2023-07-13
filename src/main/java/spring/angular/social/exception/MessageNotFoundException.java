package spring.angular.social.exception;

public class MessageNotFoundException extends RuntimeException {

    private final String message;
    public MessageNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}
