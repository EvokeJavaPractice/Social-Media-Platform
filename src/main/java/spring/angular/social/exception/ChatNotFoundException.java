package spring.angular.social.exception;

public class ChatNotFoundException extends RuntimeException {

    private String message;

    public ChatNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}
