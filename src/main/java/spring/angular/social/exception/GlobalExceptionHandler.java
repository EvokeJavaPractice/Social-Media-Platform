package spring.angular.social.exception;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@CommonsLog
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = ChatNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponse handleException(ChatNotFoundException ex) {
		log.error("ChatNotFoundException exception caught.", ex);
		return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
	}

	@ExceptionHandler(value = InvalidPasswordException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public @ResponseBody ErrorResponse handleException(InvalidPasswordException ex) {
		log.error("InvalidPasswordException exception caught.", ex);
		return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
	}

	@ExceptionHandler(value = MessageNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorResponse handleException(MessageNotFoundException ex) {
		log.error("MessageNotFoundException exception caught.", ex);
		return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
	}

	@ExceptionHandler(value = DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponse handleException(DataIntegrityViolationException ex) {
		log.error("MessageNotFoundException exception caught.", ex);
		return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getRootCause().getMessage());
	}

	@ExceptionHandler(value = ProfileNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorResponse handleException(ProfileNotFoundException ex) {
		log.error("ProfileNotFoundException exception caught.", ex);
		return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
	}

	@ExceptionHandler(value = UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorResponse handleException(UserNotFoundException ex) {
		log.error("UserNotFoundException exception caught.", ex);
		return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
	}

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        // Create a custom error response
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());

        // Return the error response with the appropriate HTTP status code
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException ex) {
        ErrorResponse errorResponse;

        if (ex.getMessage().equals("Invalid Token Signature")) {
            errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid Token Signature");
        } else {
            errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid Token");
        }

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

	@ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateAccountException(DuplicateAccountException ex) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage()), HttpStatus.CONFLICT);

    }

}
