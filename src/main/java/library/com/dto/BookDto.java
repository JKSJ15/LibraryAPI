package library.com.dto;

import java.time.LocalDate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Valid
public class BookDto {
	@NotNull
	private Long id;
	private String title;
	private String author;
	private LocalDate dateOfPublication;
	private String genre;

	public BookDto(@NotNull Long id,@NotEmpty String title, @NotEmpty String author,
			@NotEmpty LocalDate dateOfPublication, String genre) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.dateOfPublication = dateOfPublication;
		this.genre = genre;
	}

	
	public void setId(Long id) {
		this.id = id;
	}


	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public LocalDate getDateOfPublication() {
		return dateOfPublication;
	}
	public void setDateOfPublication(LocalDate dateOfPublication) {
		this.dateOfPublication = dateOfPublication;
	}
	public String getGenre() {
		return genre;
	}
	public void setGernre(String genre) {
		this.genre = genre;
	}
}
