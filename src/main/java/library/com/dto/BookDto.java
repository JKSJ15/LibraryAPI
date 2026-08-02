package library.com.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import library.com.entity.BookStatus;

public record BookDto (
	Long id,

	@NotBlank(message = "title cannot be empty!") String title,

	@NotBlank(message = "author cannot be empty!") String author,
	
	@NotNull(message = "date of publication cannot be null")
	@Schema(description = "Date of publication/creation of the book", example = "2023-01-01")
	LocalDate dateOfPublication,

	@Schema(description = "Book genre", example = "Romance")
	@NotBlank(message = "genre cannot be empty")
	String genre,

	@Schema(description = "Book description", example = "A captivating story of love and loss")
	@NotBlank(message = "description cannot be empty")
	String description,

	@Schema(description = "Current availability status of the book", example = "AVAILABLE")
	@NotNull(message = "status cannot be null")
    BookStatus status
){}