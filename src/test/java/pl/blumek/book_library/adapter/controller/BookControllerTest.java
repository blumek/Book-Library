package pl.blumek.book_library.adapter.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.adapter.controller.model.BookWeb;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.exception.BookNotFoundException;
import pl.blumek.book_library.domain.port.BookRepository;
import pl.blumek.book_library.usecase.FindBook;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookControllerTest {
    private static final String BOOK_ISBN = "BOOK_ISBN";
    private static final String BOOK_TITLE = "BOOK_TITLE";

    private BookRepository bookRepository;
    private BookController bookController;

    private Book book;
    private BookWeb expectedBook;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookController = new BookController(new FindBook(bookRepository));

        book = Book.builder()
                .isbn(BOOK_ISBN)
                .title(BOOK_TITLE)
                .build();

        expectedBook = BookWeb.builder()
                .isbn(BOOK_ISBN)
                .title(BOOK_TITLE)
                .build();
    }

    @Test
    void findByIsbnTest_BookExists() {
        when(bookRepository.findByIsbn(anyString()))
                .thenReturn(Optional.ofNullable(book));

        assertEquals(expectedBook, bookController.findByIsbn(anyString()));
    }

    @Test
    void findByIsbnTest_BookNotExist() {
        when(bookRepository.findByIsbn(anyString()))
                .thenReturn(Optional.empty());

        Exception thrown = assertThrows(BookNotFoundException.class,
                () -> bookController.findByIsbn(BOOK_ISBN));

        assertTrue(thrown.getMessage().contains(BOOK_ISBN));
    }
}