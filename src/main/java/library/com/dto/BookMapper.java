package library.com.dto;

import library.com.entity.Book;

public class BookMapper {
	public static Book toBook(BookDto dto) {
		return new Book(dto.getTitle(), dto.getAuthor(), dto.getDateOfPublication(), dto.getGenre());
	}
	public static BookDto toDto(Book book) {
		return new BookDto(book.getId(),book.getTitle(), book.getAuthor(), book.getDateOfPublication(), book.getGenre());
	}
}
