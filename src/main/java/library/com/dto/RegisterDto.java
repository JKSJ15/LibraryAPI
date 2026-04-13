package library.com.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import library.com.entity.UserRole;

public record RegisterDto (
		@Email String login,
		@NotBlank String password,
		@NotNull UserRole role
		){

}
