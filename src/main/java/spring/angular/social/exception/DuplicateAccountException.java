package spring.angular.social.exception;

public class DuplicateAccountException extends RuntimeException {

    private String message;

    public DuplicateAccountException(String msg) {
        super(msg);
        this.message = msg;
    }

}
