package library.com.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.com.dto.BookDto;
import library.com.entity.Book;
import library.com.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	private final BookService bs;
	public BookController(BookService bs) {
		super();
		this.bs = bs;
	}
	@GetMapping
	public ResponseEntity<Page<Book>> listAll(Pageable pageable){
		return new ResponseEntity<>(bs.listAll(pageable), HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<BookDto> save(@RequestBody BookDto dto) {
		return new ResponseEntity<>(bs.save(dto), HttpStatus.CREATED);
	}
}
