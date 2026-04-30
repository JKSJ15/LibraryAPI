package library.com.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Api error response")
public class ExceptionModel {
		@Schema(
	        description = "Error message",
	        example = "Book not found"
	    )
	    private String message;

	    @Schema(
	        description = "Date and time of the error",
	        example = "2026-04-23T10:15:30"
	    )
	    private LocalDateTime timestamp;

	    @Schema(
	        description = "HTTP error name",
	        example = "NOT_FOUND"
	    )
	    private HttpStatus error;

	    @Schema(
	        description = "HTTP status code",
	        example = "404"
	    )
	    private int status;
	    
	public ExceptionModel(String message, LocalDateTime timestamp, HttpStatus error, int status) {
		super();
		this.message = message;
		this.timestamp = timestamp;
		this.error = error;
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public HttpStatus getError() {
		return error;
	}
	public void setError(HttpStatus error) {
		this.error = error;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
