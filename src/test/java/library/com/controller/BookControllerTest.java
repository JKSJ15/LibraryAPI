package library.com.controller;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import library.com.dto.BookDto;
import library.com.exceptions.BookNotFoundException;
import library.com.service.BookService;
import library.com.util.BookUtilTest;

@ExtendWith(SpringExtension.class)
public class BookControllerTest {
	@InjectMocks
	BookController bc;
	@Mock
	BookService bs;
	
	@Test
	@DisplayName("Find return List of Book object when sucefull")
	void find_returnsListOfBookObject_whenSucessful() {
		PageImpl<BookDto> bookPage = new PageImpl<>(List.of(BookUtilTest.returnBookDtoGet()));

	    BDDMockito.when(bs.find(
	            ArgumentMatchers.any(),
	            ArgumentMatchers.any(),
	            ArgumentMatchers.any(),
	            ArgumentMatchers.any(Pageable.class)
	    )).thenReturn(bookPage);
		
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
		BDDMockito.when(bs.findById(ArgumentMatchers.anyLong()))
        .thenReturn(BookUtilTest.returnBookDtoGet());
		
		BookDto book = BookUtilTest.returnBookDtoGet();
		ResponseEntity<BookDto> dto = bc.findById(book.getId());
		Assertions.assertThat(dto.getBody())
        .usingRecursiveComparison()
        .isEqualTo(book);
	}
	@Test
	@DisplayName("FindById throws BooNotFoundException when not found")
	void findById_throwsBooNotFoundException_whenNotFound() {
		BDDMockito.when(bs.findById(100000L))
        .thenThrow(new BookNotFoundException("Book not found"));
		
		Assertions.assertThatThrownBy(() -> bc.findById(100000)).isInstanceOf(BookNotFoundException.class);
	}
	@Test
	@DisplayName("save return BookDto when sucessful")
	void save_returnsBookDto_WhenSucessful() {
		BDDMockito.when(bs.save(ArgumentMatchers.any(BookDto.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));
		
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
		BDDMockito.doNothing().when(bs).delete(ArgumentMatchers.anyLong());
		
	    bc.delete(1L);
	    BDDMockito.verify(bs).delete(1L);
	}
	
}
