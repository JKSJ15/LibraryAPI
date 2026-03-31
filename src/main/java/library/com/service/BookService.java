package library.com.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import library.com.dto.BookDto;
import library.com.dto.BookMapper;
import library.com.entity.Book;
import library.com.repository.BookRepository;

@Service
public class BookService {
	private final BookRepository br;
	public BookService(BookRepository br) {
		super();
		this.br = br;
	}
	public Page<Book> listAll(Pageable pageable){
		return br.findAll(pageable);
	}
	public BookDto save(BookDto dto) {
		Book toBeSaved = br.save(BookMapper.toBook(dto));
		return BookMapper.toDto(toBeSaved);
	}
}
