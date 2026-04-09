package library.com.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "title", length = 100)
	@NotEmpty(message = "title cannot be empty!")
	private String title;
	
	@Column(name = "author", length = 100)
	@NotEmpty(message = "author cannot be empty!")
	private String author;
	
	@Column(name = "dateofpublication")
	private LocalDate dateOfPublication;
	
	@Column(name = "genre", length = 100)
	private String genre;

	public Book(String title, String author, LocalDate dateOfPublication, String genre) {
		this.title = title;
		this.author = author;
		this.dateOfPublication = dateOfPublication;
		this.genre = genre;
	}
	public Book() {}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	public void setGenre(String genre) {
		this.genre = genre;
	}

}
