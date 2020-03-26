package pl.blumek.book_library.domain.port;

import pl.blumek.book_library.domain.entity.Book;

import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(String id);
    Optional<Book> findByIsbn(String isbn);
}
