package library.com.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginDto (
		@Email String login,
		@NotNull String password
		){}
