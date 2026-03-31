package library.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import library.com.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
