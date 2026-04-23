package library.com.controller;

import org.springdoc.core.annotations.ParameterObject;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import library.com.dto.BookDto;
import library.com.service.BookService;

@Tag(name = "books")
@RestController
@RequestMapping("/books")
public class BookController {
	private final BookService bs;
	public BookController(BookService bs) {
		super();
		this.bs = bs;
	}
	
	@Operation(summary = "find book by id")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Book found"),
	    @ApiResponse(responseCode = "404", description = "Book not found")
	})
	@PreAuthorize("permitAll()")
	@GetMapping("/{id}")
	public ResponseEntity<BookDto> findById(@Parameter(description = "ID of the book") @PathVariable long id){
		return new ResponseEntity<>(bs.findById(id), HttpStatus.OK);
	}
	
	@Operation(summary = "List books")
	@ApiResponse(responseCode = "200", description = "Books retrieved successfully")
	@PreAuthorize("permitAll()")
	@GetMapping()
	public ResponseEntity<Page<BookDto>> find(
			@Parameter(description = "Filter by title") @RequestParam(required = false) String title,
			@Parameter(description = "Filter by author") @RequestParam(required = false) String author,
			@Parameter(description = "Filter by genre") @RequestParam(required = false) String genre,
			@ParameterObject Pageable pageable){
		return new ResponseEntity<>(bs.find(title, author, genre, pageable), HttpStatus.OK);
	}
	
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Save a new book")
	@ApiResponses({
	    @ApiResponse(responseCode = "201", description = "Book saved successfully"),
	    @ApiResponse(responseCode = "400", description = "Invalid request data"),
	    @ApiResponse(responseCode = "403", description = "Forbidden")
	})
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<BookDto> save(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "body of book to be saved")
	@RequestBody BookDto dto) {
		return new ResponseEntity<>(bs.save(dto), HttpStatus.CREATED);
	}
	
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Delete book")
	@ApiResponses({
	    @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
	    @ApiResponse(responseCode = "404", description = "Book not found"),
	    @ApiResponse(responseCode = "403", description = "Forbidden")
	})
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@Parameter(description = "ID of the book") @PathVariable long id){
		bs.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "update book")
	@ApiResponses({
		@ApiResponse (responseCode = "200", description = "Book updated successfully"),
	    @ApiResponse(responseCode = "400", description = "Invalid request data"),
		@ApiResponse(responseCode = "404", description = "Book not found"),
	    @ApiResponse(responseCode = "403", description = "Forbidden")
		})
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<BookDto> update(@Parameter(description = "ID of the book") @PathVariable long id, 
		@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "new body of book to be updated")
		@Valid @RequestBody BookDto dto){
		return new ResponseEntity<>(bs.update(id, dto), HttpStatus.OK);
	}
	
}
