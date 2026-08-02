package library.com.exceptions.Handler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import library.com.exceptions.BookNotFoundException;
import library.com.exceptions.ExceptionModel;
import library.com.exceptions.InvalidDateException;
import library.com.exceptions.InvalidRefreshTokenException;
import library.com.exceptions.InvalidTokenException;
import library.com.exceptions.UnauthorizedException;
import library.com.exceptions.UserAlreadyExistsException;
import library.com.exceptions.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionsHandler {
	@ExceptionHandler()
	public ResponseEntity<ExceptionModel> useBookNotFoundException(BookNotFoundException e){
		ExceptionModel model = new ExceptionModel(e.getMessage(), LocalDateTime.now()
				, HttpStatus.NOT_FOUND , HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
	}
	@ExceptionHandler()
	public ResponseEntity<ExceptionModel> useInvalidDateException(InvalidDateException e){
		ExceptionModel model = new ExceptionModel(e.getMessage(), LocalDateTime.now()
				, HttpStatus.BAD_REQUEST , HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(model);
	}
	@ExceptionHandler()
	public ResponseEntity<ExceptionModel> useUserNotFoundException(UserNotFoundException e){
		ExceptionModel model = new ExceptionModel(e.getMessage(), LocalDateTime.now()
				, HttpStatus.NOT_FOUND , HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
	}
	@ExceptionHandler()
	public ResponseEntity<ExceptionModel> useUserAlreadyExistsException(UserAlreadyExistsException e){
		ExceptionModel model = new ExceptionModel(e.getMessage(), LocalDateTime.now()
				, HttpStatus.CONFLICT , HttpStatus.CONFLICT.value());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(model);
	}
	@ExceptionHandler()
	public ResponseEntity<ExceptionModel> useInvalidTokenException(InvalidTokenException e){
		ExceptionModel model = new ExceptionModel(e.getMessage(), LocalDateTime.now()
				, HttpStatus.UNAUTHORIZED , HttpStatus.UNAUTHORIZED.value());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(model);
	}
	@ExceptionHandler()
	public ResponseEntity<ExceptionModel> useUnauthorizedException(UnauthorizedException e){
		ExceptionModel model = new ExceptionModel(e.getMessage(), LocalDateTime.now()
				, HttpStatus.UNAUTHORIZED , HttpStatus.UNAUTHORIZED.value());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(model);
	}
	@ExceptionHandler()
	public ResponseEntity<ExceptionModel> useInvalidRefreshTokenException(InvalidRefreshTokenException e){
		ExceptionModel model = new ExceptionModel(e.getMessage(), LocalDateTime.now()
				, HttpStatus.UNAUTHORIZED , HttpStatus.UNAUTHORIZED.value());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(model);
	}
	@ExceptionHandler()
	public ResponseEntity<ExceptionModel> useMethodArgumentNotValidException(MethodArgumentNotValidException e){
		ExceptionModel model = new ExceptionModel(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage(), LocalDateTime.now()
				, HttpStatus.BAD_REQUEST , HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(model);
	}
	@ExceptionHandler()
	public ResponseEntity<ExceptionModel> useConstraintViolationException(ConstraintViolationException e){
		ExceptionModel model = new ExceptionModel(e.getConstraintViolations()
            .stream()
            .findFirst()
            .get()
            .getMessage(), LocalDateTime.now()
				, HttpStatus.BAD_REQUEST , HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(model);
	}
	@ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionModel> handleAccessDeniedException(
            AccessDeniedException e
    ){

        ExceptionModel model = new ExceptionModel(
                "You don't have permission to access this resource",
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN,
                HttpStatus.FORBIDDEN.value()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(model);
    }
	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ExceptionModel> handleAuthorizationDeniedException(
        AuthorizationDeniedException e
	) {

    ExceptionModel model = new ExceptionModel(
            "You don't have permission to access this resource",
            LocalDateTime.now(),
            HttpStatus.FORBIDDEN,
            HttpStatus.FORBIDDEN.value()
    );

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(model);
}
}
