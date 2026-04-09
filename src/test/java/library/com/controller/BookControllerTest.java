package library.com.controller;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import library.com.dto.BookDto;
import library.com.dto.BookMapper;
import library.com.entity.Book;
import library.com.exceptions.BookNotFoundException;
import library.com.service.BookService;
import library.com.util.BookUtilTest;

@ExtendWith(SpringExtension.class)
public class BookControllerTest {
	@InjectMocks
	BookController bc;
	@Mock
	BookService bs;
	
	@BeforeEach // isso isola a classe controller e não depende da logica nem nada, so diz que vai voltar um page
	void setUp() {
		MockitoAnnotations.openMocks(this);
	PageImpl<BookDto> bookPage = new PageImpl<>(List.of(BookUtilTest.returnBookDtoGet()));
	BDDMockito.when(bs.find(
	        ArgumentMatchers.anyString(),
	        ArgumentMatchers.anyString(),
	        ArgumentMatchers.anyString(),
	        ArgumentMatchers.any(Pageable.class)
	)).thenReturn(bookPage);
		
	BDDMockito.when(bs.findById(ArgumentMatchers.anyLong()))
        .thenReturn(BookUtilTest.returnBookDtoGet());
	
	BDDMockito.when(bs.findById(100000L))
    .thenThrow(new BookNotFoundException("Book not found"));
	
	BDDMockito.when(bs.save(ArgumentMatchers.any(BookDto.class)))
    .thenReturn(BookUtilTest.returnBookDtoGet());
	
	BDDMockito.doNothing().when(bs).delete(ArgumentMatchers.anyLong());
	
	
	}
	@Test
	@DisplayName("Find return List of Book object when sucefull")
	void find_returnsListOfBookObject_whenSucessful() {
		BookDto book = BookUtilTest.returnBookDtoGet();
		Pageable pageable = Pageable.unpaged();
		ResponseEntity<Page<BookDto>> list = bc.find(book.getTitle(), book.getAuthor() , book.getGenre(), pageable);
		
		Assertions.assertThat(list.getStatusCode().is2xxSuccessful()).isTrue();
		Assertions.assertThat(list)
        .isNotNull();
		Assertions.assertThat(list.getBody().getContent()).isNotEmpty();
	}
	@Test
	@DisplayName("FindById returns BookDto object when sucefull")
	void findById_returnsBookDtoObject_whenSucessful() {
		BookDto book = BookUtilTest.returnBookDtoGet();
		ResponseEntity<BookDto> dto = bc.findById(book.getId());
		Assertions.assertThat(dto.getBody())
        .usingRecursiveComparison()
        .isEqualTo(book);
	}
	@Test
	@DisplayName("FindById throws BooNotFoundException when not found")
	void findById_throwsBooNotFoundException_whenNotFound() {
		Assertions.assertThatThrownBy(() -> bc.findById(100000)).isInstanceOf(BookNotFoundException.class);
	}
	@Test
	@DisplayName("save return BookDto when sucessful")
	void save_returnsBookDto_WhenSucessful() {
		BookDto dto = BookUtilTest.returnBookDtoGet();
		ResponseEntity<BookDto> dtoSaved = bc.save(dto);
		
		Assertions.assertThat(dtoSaved.getStatusCode().is2xxSuccessful()).isTrue();
		Assertions.assertThat(dtoSaved).isNotNull();
		Assertions.assertThat(dtoSaved.getBody())
	    .usingRecursiveComparison()
	    .isEqualTo(dto);
	}
	@Test
	@DisplayName("delete remove BookDto when sucessful")
	void delete_removeBookDto_WhenSucessful() {
	    bc.delete(1L);
	    BDDMockito.verify(bs).delete(1L);
	}
	
}
