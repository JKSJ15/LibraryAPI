package library.com.service;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public BookDto findById(long id) {
		Book found = br.findById(id).orElseThrow(()-> new BookNotFoundException("Book not found"));
		return BookMapper.toDto(found);		
	}
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
	public BookDto save(BookDto dto) {
		LocalDate limit = LocalDate.of(1, 01, 01);
		if (dto.getDateOfPublication().isBefore(limit) ||
			dto.getDateOfPublication().isAfter(LocalDate.now())) {
		throw new InvalidDateException("Invalid date!");	
		}
		Book toBeSaved = br.save(BookMapper.toBook(dto));
		return BookMapper.toDto(toBeSaved);
	}
	
	public void delete(long id) {
		Book toBeDelete = br.findById(id).orElseThrow(()-> new BookNotFoundException("Book not found"));
		br.delete(toBeDelete);
	}
	public BookDto update(long id, BookDto dto) {
		Book find = br.findById(id).orElseThrow(()-> new BookNotFoundException("Book not found"));
		find.setAuthor(dto.getAuthor());
		find.setDateOfPublication(dto.getDateOfPublication());
		find.setGenre(dto.getGenre());
		find.setTitle(dto.getTitle());
		find.setStatus(dto.getStatus());
		find.setDescription(dto.getDescription());
		br.save(find);
		return BookMapper.toDto(find);
	}
}
