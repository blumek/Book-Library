package pl.blumek.book_library.usecase;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.entity.Category;
import pl.blumek.book_library.domain.port.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

class FindBookTest {
    private static final String BOOK_ISBN = "BOOK_ISBN";
    private static final String BOOK_ISBN_NOT_EXISTS = "BOOK_ISBN_NOT_EXISTS";
    private static final String BOOK_ID = "BOOK_ID";
    private static final String BOOK_ANOTHER_ID = "BOOK_ANOTHER_ID";
    private static final String BOOK_TITLE = "BOOK_TITLE";
    private static final String CATEGORY_NAME = "CATEGORY_NAME";
    private static final String CATEGORY_NAME_NOT_EXISTS = "CATEGORY_NAME_NOT_EXISTS";
    private static final String ANOTHER_CATEGORY_NAME = "ANOTHER_CATEGORY_NAME";

    private BookRepository repository;
    private FindBook findBook;

    private Book bookWithIsbn;
    private Book bookWithoutIsbn;

    @BeforeEach
    void setUp() {
        bookWithIsbn = Book.builder()
                .id(BOOK_ANOTHER_ID)
                .isbn(BOOK_ISBN)
                .title(BOOK_TITLE)
                .category(Category.builder()
                        .name(CATEGORY_NAME)
                        .build())
                .category(Category.builder()
                        .name(ANOTHER_CATEGORY_NAME)
                        .build())
                .build();

        bookWithoutIsbn = Book.builder()
                .id(BOOK_ID)
                .title(BOOK_TITLE)
                .category(Category.builder()
                        .name(CATEGORY_NAME)
                        .build())
                .build();

        repository = mock(BookRepository.class);
        findBook = new FindBook(repository);
    }

    @Test
    void findByIsbnTest_BookNotExists() {
        when(repository.findByIsbn(anyString()))
                .thenReturn(Optional.empty());

        when(repository.findById(anyString()))
                .thenReturn(Optional.empty());

        Optional<Book> book = findBook.findByIsbn(BOOK_ISBN_NOT_EXISTS);

        assertEquals(Optional.empty(), book);
    }

    @Test
    void findByIsbnTest_BookExists() {
        when(repository.findByIsbn(anyString()))
                .thenReturn(Optional.of(bookWithIsbn));

        Optional<Book> book = findBook.findByIsbn(BOOK_ISBN);

        assertEquals(Optional.of(bookWithIsbn), book);
    }

    @Test
    void findByIsbnTest_BookWithoutIsbn() {
        when(repository.findByIsbn(anyString()))
                .thenReturn(Optional.empty());

        when(repository.findById(anyString()))
                .thenReturn(Optional.of(bookWithoutIsbn));

        Optional<Book> book = findBook.findByIsbn(BOOK_ID);

        assertEquals(Optional.of(bookWithoutIsbn), book);
    }

    @Test
    void findAllByCategoryName_TwoBooksWithGivenCategory() {
        when(repository.findAllByCategoryName(anyString()))
                .thenReturn(Lists.newArrayList(bookWithIsbn, bookWithoutIsbn));

        List<Book> books = findBook.findAllByCategoryName(CATEGORY_NAME);

        assertThat(books, containsInAnyOrder(bookWithIsbn, bookWithoutIsbn));
    }

    @Test
    void findAllByCategoryName_BooksWithGivenCategoryNotExist() {
        when(repository.findAllByCategoryName(anyString()))
                .thenReturn(Lists.newArrayList());

        List<Book> books = findBook.findAllByCategoryName(CATEGORY_NAME_NOT_EXISTS);

        assertIterableEquals(Lists.newArrayList(), books);
    }
}