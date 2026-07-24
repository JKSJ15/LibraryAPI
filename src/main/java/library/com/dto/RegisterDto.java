package library.com.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDto (
		@Email String login,
		@NotBlank String password
		){

}
