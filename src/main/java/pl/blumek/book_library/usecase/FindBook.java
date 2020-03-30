package pl.blumek.book_library.usecase;

import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.port.BookRepository;

import java.util.List;
import java.util.Optional;

public final class FindBook {
    private final BookRepository repository;

    public FindBook(BookRepository repository) {
        this.repository = repository;
    }

    public Optional<Book> findByIsbn(String isbn) {
        Optional<Book> book = repository.findByIsbn(isbn);
        if (!book.isPresent())
            book = repository.findById(isbn);
        return book;
    }

    public List<Book> findAllByCategoryName(String categoryName) {
        return repository.findAllByCategoryName(categoryName);
    }
}
