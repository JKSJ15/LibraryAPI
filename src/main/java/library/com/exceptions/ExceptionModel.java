package library.com.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ExceptionModel {
	private String message;
	private LocalDateTime timestamp;
	private HttpStatus error;
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
