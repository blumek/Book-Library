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
        Optional<Book> book = getBookWithIsbn(isbn);
        if (!book.isPresent())
            book = getBookWithId(isbn);
        return book;
    }

    private Optional<Book> getBookWithIsbn(String isbn) {
        return repository.findByIsbn(isbn);
    }

    private Optional<Book> getBookWithId(String isbn) {
        return repository.findById(isbn);
    }

    public List<Book> findAllByCategoryName(String categoryName) {
        return repository.findAllByCategoryName(categoryName);
    }
}
