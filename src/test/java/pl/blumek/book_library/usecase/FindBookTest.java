package pl.blumek.book_library.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.adapter.repository.InMemoryBookRepository;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.port.BookRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FindBookTest {
    private static final String BOOK_ISBN = "BOOK_ISBN";
    private static final String BOOK_ID = "BOOK_ID";
    public static final String BOOK_TITLE = "BOOK_TITLE";
    public static final String ISBN_OR_ID_NOT_EXIST = "NOT_EXIST";

    private FindBook findBook;
    private Book bookWithIsbn;
    private Book bookWithoutIsbn;

    @BeforeEach
    void setUp() {
        bookWithIsbn = Book.builder()
                .id("BOOK_ANOTHER_ID")
                .isbn(BOOK_ISBN)
                .title(BOOK_TITLE)
                .build();

        bookWithoutIsbn = Book.builder()
                .id(BOOK_ID)
                .title(BOOK_TITLE)
                .build();

        BookRepository repository = new InMemoryBookRepository(bookWithIsbn, bookWithoutIsbn);
        findBook = new FindBook(repository);
    }

    @Test
    void findByIsbnTest_BookNotExist() {
        Optional<Book> book = findBook.findByIsbn(ISBN_OR_ID_NOT_EXIST);
        assertEquals(Optional.empty(), book);
    }

    @Test
    void findByIsbnTest_BookExists() {
        Optional<Book> book = findBook.findByIsbn(BOOK_ISBN);
        assertEquals(Optional.of(bookWithIsbn), book);
    }

    @Test
    void findByIsbnTest_BookWithoutIsbn() {
        Optional<Book> book = findBook.findByIsbn(BOOK_ID);
        assertEquals(Optional.of(bookWithoutIsbn), book);
    }
}