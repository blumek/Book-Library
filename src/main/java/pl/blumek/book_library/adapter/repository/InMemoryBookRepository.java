package pl.blumek.book_library.adapter.repository;

import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.entity.Person;
import pl.blumek.book_library.domain.port.BookRepository;

import java.util.*;

import static java.util.stream.Collectors.*;

public class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> inMemoryDb;

    public InMemoryBookRepository(Book... books) {
        this.inMemoryDb = Arrays.stream(books)
                .collect(toMap(this::getId, book -> book));
    }

    private String getId(Book book) {
        return hasId(book) ? book.getId() : UUID.randomUUID().toString();
    }

    private boolean hasId(Book book) {
        return book.getId() != null && !book.getId().isEmpty();
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

    @Override
    public List<Book> findAllByCategoryName(String categoryName) {
        return inMemoryDb.values().stream()
                .filter(book -> book.getCategories().stream()
                        .anyMatch(category -> categoryName.equals(category.getName())))
                .collect(toList());
    }

    @Override
    public List<Book> findAllByAuthorName(String authorName) {
        return inMemoryDb.values().stream()
                .filter(book -> book.getAuthors().stream()
                        .anyMatch(author -> authorName.equals(author.getFullName())))
                .collect(toList());
    }
}
