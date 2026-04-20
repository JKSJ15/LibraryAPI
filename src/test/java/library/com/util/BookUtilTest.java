package library.com.util;

import java.time.LocalDate;
import library.com.dto.BookDto;
import library.com.dto.BookMapper;
import library.com.entity.Book;
import library.com.entity.BookStatus;

public class BookUtilTest {
	public static BookDto returnBookDtoGet() {
		Book book = new Book("kaka", "jopao", LocalDate.now(), "romance", "a book of girls dream", BookStatus.AVAILABLE);
		book.setId(2l);
		return BookMapper.toDto(book);
	}
	public static Book returnBookPostOrPut() {
		Book book = new Book("kaka", "jopao", LocalDate.now(), "romance",  "a book of girls dream", BookStatus.AVAILABLE);
		return book;
	}
}
