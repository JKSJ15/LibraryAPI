package library.com.dto;

import java.time.LocalDate;

public class BookDto {
	private String title;
	private String author;
	private LocalDate dateOfPublication;
	private String gernre;

	public BookDto(String title, String author, LocalDate dateOfPublication, String gernre) {
		this.title = title;
		this.author = author;
		this.dateOfPublication = dateOfPublication;
		this.gernre = gernre;
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
