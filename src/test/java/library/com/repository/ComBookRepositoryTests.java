package library.com.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import library.com.entity.Book;
import library.com.util.BookUtilTest;

@DataJpaTest
public class ComBookRepositoryTests {
	@Autowired
	private BookRepository br;

	@Test
	@DisplayName("save persiste book when sucefull")
	public void save_persisteBook_WhenSucefull() {
		Book book = BookUtilTest.returnBookPostOrPut();
		Book bookSaved = this.br.save(book);
		Assertions.assertThat(bookSaved).isNotNull();
		Assertions.assertThat(bookSaved.getId()).isNotNull();
		Assertions.assertThat(bookSaved.getTitle()).isNotNull();
		Assertions.assertThat(bookSaved.getAuthor()).isNotNull();
		Assertions.assertThat(bookSaved.getTitle().equals(book.getTitle()));
		Assertions.assertThat(bookSaved.getAuthor().equals(book.getAuthor()));		
	}
	@Test
	@DisplayName("save updastes book when sucefull")
	public void save_updatesBook_WhenSucefull() {
		Book book = BookUtilTest.returnBookPostOrPut();
		Book bookSaved = this.br.save(book);
		bookSaved.setAuthor("authorSet");
		Book bookUpdated = this.br.save(bookSaved);
		
		Assertions.assertThat(bookUpdated).isNotNull();
		Assertions.assertThat(bookUpdated.getId()).isNotNull();
		Assertions.assertThat(bookUpdated.getAuthor()).isNotNull();
		Assertions.assertThat(bookSaved)
	    .usingRecursiveComparison()
	    .isEqualTo(bookUpdated);	
	}
	@Test
	@DisplayName("delete removes book when sucefull")
	public void delete_RemovesBook_WhenSucefull() {
		Book book = BookUtilTest.returnBookPostOrPut();
		Book bookSaved = this.br.save(book);
		this.br.delete(bookSaved);
		Optional<Book> bookOptional = this.br.findById(bookSaved.getId());	
		Assertions.assertThat(bookOptional).isEmpty();
	}
	@Test
	@DisplayName("findByAuthor returns empty when not found")
	public void findByAuthor_ReturnsEmpty_WhenNOtFound() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Book> books = this.br.findByAuthorContainingIgnoreCase("cachu55", pageable);
		Assertions.assertThat(books).isEmpty();
	}
	@Test
	@DisplayName("findByATitle returns empty when not found")
	public void findByTitle_ReturnsEmpty_WhenNOtFound() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Book> books = this.br.findByTitleContainingIgnoreCase("cachu55", pageable);
		Assertions.assertThat(books).isEmpty();
	}
	@Test
	@DisplayName("findByGenre returns empty when not found")
	public void findByGenre_ReturnsEmpty_WhenNOtFound() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Book> books = this.br.findByGenreContainingIgnoreCase("cachu55", pageable);
		Assertions.assertThat(books).isEmpty();
	}
}
