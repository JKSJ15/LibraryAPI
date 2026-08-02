package library.com.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import library.com.dto.BookDto;
import library.com.dto.BookMapper;
import library.com.entity.Book;
import library.com.exceptions.BookNotFoundException;
import library.com.exceptions.InvalidDateException;
import library.com.repository.BookRepository;

@Service
public class BookService {
	private final BookRepository br;
	public BookService(BookRepository br) {
		super();
		this.br = br;
	}
	@PreAuthorize("permitAll()")
	public BookDto findById(long id) {
		Book found = findByIdOrThrow(id);
		return BookMapper.toDto(found);		
	}
	@PreAuthorize("permitAll()")
	public Page<BookDto> find(String title, String author, String genre, Pageable pageable) {
		Page<Book> books;
		if(title!=null) {
			 books = br.findByTitleContainingIgnoreCase(title, pageable);
		}else if (author!=null) {
			 books = br.findByAuthorContainingIgnoreCase(author, pageable);
		}else if (genre!=null) {
			 books = br.findByGenreContainingIgnoreCase(genre, pageable);
		} else {
			books = br.findAll(pageable);
		}
		return books.map(BookMapper::toDto);
	}
	@PreAuthorize("hasRole('ADMIN')")
	public BookDto save(BookDto dto) {
		validateDate(dto.dateOfPublication());
		Book toBeSaved = br.save(BookMapper.toBook(dto));
		return BookMapper.toDto(toBeSaved);
	}
	@PreAuthorize("hasRole('ADMIN')")
	public void delete(long id) {
		Book toBeDelete = findByIdOrThrow(id);
		br.delete(toBeDelete);
	}
	@PreAuthorize("hasRole('ADMIN')")
	public BookDto update(long id, BookDto dto) {
		validateDate(dto.dateOfPublication());
		Book find = findByIdOrThrow(id);
		find.setAuthor(dto.author());
		find.setDateOfPublication(dto.dateOfPublication());
		find.setGenre(dto.genre());
		find.setTitle(dto.title());
		find.setStatus(dto.status());
		find.setDescription(dto.description());
		br.save(find);
		return BookMapper.toDto(find);
	}
	private void validateDate(LocalDate date) {
		LocalDate limit = LocalDate.of(1, 01, 01);
		if (date.isBefore(limit) || date.isAfter(LocalDate.now())) {
			throw new InvalidDateException("Invalid date!");
		}
	}
	private Book findByIdOrThrow(long id) {
		return br.findById(id).orElseThrow(()-> new BookNotFoundException("Book not found"));
	}
}
