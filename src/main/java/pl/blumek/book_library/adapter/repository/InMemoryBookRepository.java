package pl.blumek.book_library.adapter.repository;

import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.port.BookRepository;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

public class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> inMemoryDb;

    public InMemoryBookRepository(Book... books) {
        this.inMemoryDb = Arrays.stream(books)
                .collect(toMap(Book::getId, book -> book));
    }

    @Override
    public Optional<Book> findById(String id) {
        return Optional.ofNullable(inMemoryDb.get(id));
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return inMemoryDb.values().stream()
                .filter(book -> isbn.equals(book.getIsbn()))
                .findAny();
    }
}
