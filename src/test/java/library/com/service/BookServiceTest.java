package library.com.service;

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
import org.mockito.junit.jupiter.MockitoExtension;
import library.com.dto.BookDto;
import library.com.dto.BookMapper;
import library.com.entity.Book;
import library.com.exceptions.BookNotFoundException;
import library.com.repository.BookRepository;
import library.com.util.BookUtilTest;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bs;

    @Mock
    private BookRepository br;

    @Mock
    private BookMapper mapper;

    private Book book;
    private BookDto dto;

    @BeforeEach
    void setUp() {
        book = BookUtilTest.returnBookPostOrPut();
        dto = BookUtilTest.returnBookDtoGet();
        dto.setId(null);
    }

    @Test
    @DisplayName("findById returns BookDto when successful")
    void findById_returnsBookDto_whenSuccessful() {
        BDDMockito.when(br.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(book));

        BookDto response = bs.findById(2L);

        Assertions.assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(dto);
    }

    @Test
    @DisplayName("findById throws exception when not found")
    void findById_throwsException_whenNotFound() {
        BDDMockito.when(br.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> bs.findById(1L))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    @DisplayName("save creates book when successful")
    void save_createsBook_whenSuccessful() {
        BDDMockito.when(br.save(ArgumentMatchers.any(Book.class)))
                .thenReturn(book);

        BookDto response = bs.save(dto);

        Assertions.assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(dto);

        BDDMockito.verify(br, BDDMockito.times(1)).save(ArgumentMatchers.any(Book.class));
    }

    @Test
    @DisplayName("delete removes book when successful")
    void delete_removesBook_whenSuccessful() {
        BDDMockito.when(br.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(book));

        BDDMockito.doNothing().when(br).delete(book);

        bs.delete(1L);

        BDDMockito.verify(br, BDDMockito.times(1)).delete(book);
    }

    @Test
    @DisplayName("delete throws exception when book not found")
    void delete_throwsException_whenNotFound() {
        BDDMockito.when(br.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> bs.delete(1L))
                .isInstanceOf(BookNotFoundException.class);
    }
}