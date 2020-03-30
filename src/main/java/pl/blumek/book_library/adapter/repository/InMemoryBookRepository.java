package pl.blumek.book_library.adapter.repository;

import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.port.BookRepository;
import pl.blumek.book_library.domain.port.IdGenerator;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public final class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> inMemoryDb;
    private final IdGenerator idGenerator;

    public InMemoryBookRepository(IdGenerator idGenerator, Book... books) {
        this(idGenerator, Arrays.stream(books));
    }

    public InMemoryBookRepository(IdGenerator idGenerator, List<Book> books) {
        this(idGenerator, books.stream());
    }

    public InMemoryBookRepository(IdGenerator idGenerator, Stream<Book> bookStream) {
        this.idGenerator = idGenerator;
        this.inMemoryDb = bookStream
                .collect(toMap(this::getId, book -> book));
    }

    private String getId(Book book) {
        return hasId(book) ? book.getId() : idGenerator.generate();
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
