package library.com.service;

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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import library.com.controller.BookController;
import library.com.dto.BookDto;
import library.com.entity.Book;
import library.com.exceptions.BookNotFoundException;
import library.com.util.BookUtilTest;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {
	@InjectMocks
	BookService bs;
	@Mock
	BookController bc;
	

}
