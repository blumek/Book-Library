package pl.blumek.book_library.adapter.repository;

import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.entity.Category;
import pl.blumek.book_library.domain.entity.Person;
import pl.blumek.book_library.domain.port.BookRepository;
import pl.blumek.book_library.domain.port.IdGenerator;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public final class InMemoryBookRepository extends InMemoryRepository<Book> implements BookRepository {
    public InMemoryBookRepository(IdGenerator idGenerator, Book... t) {
        super(idGenerator, t);
    }

    public InMemoryBookRepository(IdGenerator idGenerator, List<Book> t) {
        super(idGenerator, t);
    }

    public InMemoryBookRepository(IdGenerator idGenerator, Stream<Book> bookStream) {
        super(idGenerator, bookStream);
    }

    @Override
    Book entityWithAssignedId(Book book) {
        return book.toBuilder()
                .id(getId(book))
                .build();
    }

    private String getId(Book book) {
        return hasId(book) ? book.getId() : generateId();
    }

    private boolean hasId(Book book) {
        return book.getId() != null && !book.getId().isEmpty();
    }

    @Override
    String getEntityId(Book book) {
        return book.getId();
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
                        .anyMatch(category -> equalCategoryNames(categoryName, category)))
                .collect(toList());
    }

    private boolean equalCategoryNames(String categoryName, Category category) {
        return categoryName.equalsIgnoreCase(category.getName());
    }

    @Override
    public List<Book> findAllByAuthorName(String authorName) {
        return inMemoryDb.values().stream()
                .filter(book -> book.getAuthors().stream()
                        .anyMatch(author -> equalAuthorNames(authorName, author)))
                .collect(toList());
    }

    private boolean equalAuthorNames(String authorName, Person author) {
        return authorName.equalsIgnoreCase(author.getFullName());
    }
}
