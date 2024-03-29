package pl.blumek.book_library.adapter.controller;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.adapter.controller.model.BookWeb;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.usecase.FindBook;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

class BookControllerTest {
    private static final String BOOK_ISBN = "BOOK_ISBN";
    private static final String BOOK_TITLE = "BOOK_TITLE";
    private static final String CATEGORY_NAME = "CATEGORY_NAME";
    private static final String ANOTHER_BOOK_ISBN = "ANOTHER_BOOK_ISBN";
    private static final String ANOTHER_BOOK_TITLE = "ANOTHER_BOOK_TITLE";

    private FindBook findBook;
    private BookController bookController;

    private Book book;
    private BookWeb expectedBook;
    private Book anotherBook;
    private BookWeb anotherExpectedBook;

    @BeforeEach
    void setUp() {
        findBook = mock(FindBook.class);
        bookController = new BookController(findBook);

        book = Book.builder()
                .isbn(BOOK_ISBN)
                .title(BOOK_TITLE)
                .build();

        expectedBook = BookWeb.builder()
                .isbn(BOOK_ISBN)
                .title(BOOK_TITLE)
                .build();

        anotherBook = Book.builder()
                .isbn(ANOTHER_BOOK_ISBN)
                .title(ANOTHER_BOOK_TITLE)
                .build();

        anotherExpectedBook = BookWeb.builder()
                .isbn(ANOTHER_BOOK_ISBN)
                .title(ANOTHER_BOOK_TITLE)
                .build();
    }

    @Test
    void findByIsbnTest_BookExists() {
        when(findBook.findByIsbn(anyString()))
                .thenReturn(Optional.ofNullable(book));

        assertEquals(Optional.of(expectedBook), bookController.findByIsbn(BOOK_ISBN));
    }

    @Test
    void findByIsbnTest_BookNotExists() {
        when(findBook.findByIsbn(anyString()))
                .thenReturn(Optional.empty());

        assertEquals(Optional.empty(), bookController.findByIsbn(BOOK_ISBN));
    }

    @Test
    void findAllByCategoryName_BooksInGivenCategoryNotExist() {
        when(findBook.findAllByCategoryName(anyString()))
                .thenReturn(Lists.newArrayList());

        assertIterableEquals(Lists.newArrayList(), bookController.findAllByCategoryName(CATEGORY_NAME));
    }

    @Test
    void findAllByCategoryName_BooksInGivenCategoryExist() {
        when(findBook.findAllByCategoryName(anyString()))
                .thenReturn(Lists.newArrayList(book, anotherBook));

        assertThat(bookController.findAllByCategoryName(CATEGORY_NAME),
                containsInAnyOrder(expectedBook, anotherExpectedBook));
    }
}