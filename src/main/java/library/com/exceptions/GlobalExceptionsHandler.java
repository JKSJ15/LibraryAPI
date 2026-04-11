package library.com.exceptions;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionsHandler {
	@ExceptionHandler()
	public ResponseEntity<ExceptionModel> useBookNotFoundException(BookNotFoundException e){
		ExceptionModel model = new ExceptionModel(e.getMessage(), LocalDateTime.now()
				, HttpStatus.BAD_REQUEST , HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(model);
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
				, HttpStatus.BAD_REQUEST , HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(model);
	}
	@ExceptionHandler()
	public ResponseEntity<ExceptionModel> useUserAlreadyExistsException(UserAlreadyExistsException e){
		ExceptionModel model = new ExceptionModel(e.getMessage(), LocalDateTime.now()
				, HttpStatus.BAD_REQUEST , HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(model);
	}
}
