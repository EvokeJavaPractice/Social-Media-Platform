package spring.angular.social.exception;

public class ProfileNotFoundException extends RuntimeException {

    private String message;

    public ProfileNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}
