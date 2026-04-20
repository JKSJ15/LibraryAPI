package library.com.dto;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import library.com.entity.BookStatus;

@Valid
public class BookDto {
	private Long id;
	private String title;
	private String author;
	private LocalDate dateOfPublication;
	private String genre;
	private String description;
    private BookStatus status;

	public BookDto(Long id, String title, String author, LocalDate dateOfPublication, String genre, String description,
			BookStatus status) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.dateOfPublication = dateOfPublication;
		this.genre = genre;
		this.description = description;
		this.status = status;
	}


	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BookStatus getStatus() {
		return status;
	}
	public void setStatus(BookStatus status) {
		this.status = status;
	}
	public void setGenre(String genre) {
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


	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
}
