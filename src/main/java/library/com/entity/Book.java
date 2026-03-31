package library.com.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "title", length = 100)
	private String title;
	
	@Column(name = "author", length = 100)
	private String author;
	
	@Column(name = "dateofpublication")
	private LocalDate dateOfPublication;
	
	@Column(name = "genre", length = 100)
	private String gernre;

	public Book(String title, String author, LocalDate dateOfPublication, String gernre) {
		this.title = title;
		this.author = author;
		this.dateOfPublication = dateOfPublication;
		this.gernre = gernre;
	}
	public Book() {}
	
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
	public String getGernre() {
		return gernre;
	}
	public void setGernre(String gernre) {
		this.gernre = gernre;
	}

}
