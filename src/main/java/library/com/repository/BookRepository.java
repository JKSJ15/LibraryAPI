package library.com.repository;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import library.com.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
	public Page<Book> findByTitleIgnoreCase(String title, Pageable pageable);
	public Page<Book> findByAuthorIgnoreCase(String author, Pageable pageable);
	public Page<Book> findByGenreIgnoreCase(String genre, Pageable pageable);
	public Page<Book> findByDateOfPublication(LocalDate dateOfPublication, Pageable pageable);
}
