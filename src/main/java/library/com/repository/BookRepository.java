package library.com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import library.com.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
	public Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
	public Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);
	public Page<Book> findByGenreContainingIgnoreCase(String genre, Pageable pageable);
}
