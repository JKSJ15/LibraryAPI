package library.com.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import library.com.dto.BookDto;
import library.com.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	private final BookService bs;
	public BookController(BookService bs) {
		super();
		this.bs = bs;
	}
	@PreAuthorize("permitAll()")
	@GetMapping("/{id}")
	public ResponseEntity<BookDto> findById(@PathVariable long id){
		return new ResponseEntity<>(bs.findById(id), HttpStatus.OK);
	}
	@PreAuthorize("permitAll()")
	@GetMapping()
	public ResponseEntity<Page<BookDto>> find(@RequestParam(required = false) String title,
			@RequestParam(required = false) String author,
			@RequestParam(required = false) String genre,
			Pageable pageable){
		return new ResponseEntity<>(bs.find(title, author, genre, pageable), HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<BookDto> save(@RequestBody @Valid BookDto dto) {
		return new ResponseEntity<>(bs.save(dto), HttpStatus.CREATED);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		bs.delete(id);
		return ResponseEntity.noContent().build();
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<BookDto> update(@PathVariable long id, @RequestBody BookDto dto){
		return new ResponseEntity<>(bs.update(id, dto), HttpStatus.OK);
	}
	
}
