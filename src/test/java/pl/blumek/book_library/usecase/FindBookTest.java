package pl.blumek.book_library.usecase;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.entity.Category;
import pl.blumek.book_library.domain.port.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindBookTest {
    private static final String BOOK_ISBN = "BOOK_ISBN";
    private static final String BOOK_ID = "BOOK_ID";
    public static final String BOOK_ANOTHER_ID = "BOOK_ANOTHER_ID";
    public static final String BOOK_TITLE = "BOOK_TITLE";
    public static final String CATEGORY_NAME = "CATEGORY_NAME";
    public static final String ANOTHER_CATEGORY_NAME = "ANOTHER_CATEGORY_NAME";

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
    void findByIsbnTest_BookNotExist() {
        when(repository.findByIsbn(anyString()))
                .thenReturn(Optional.empty());

        when(repository.findById(anyString()))
                .thenReturn(Optional.empty());

        Optional<Book> book = findBook.findByIsbn(anyString());

        assertEquals(Optional.empty(), book);
    }

    @Test
    void findByIsbnTest_BookExists() {
        when(repository.findByIsbn(anyString()))
                .thenReturn(Optional.of(bookWithIsbn));

        Optional<Book> book = findBook.findByIsbn(anyString());

        assertEquals(Optional.of(bookWithIsbn), book);
    }

    @Test
    void findByIsbnTest_BookWithoutIsbn() {
        when(repository.findByIsbn(anyString()))
                .thenReturn(Optional.empty());

        when(repository.findById(anyString()))
                .thenReturn(Optional.of(bookWithoutIsbn));

        Optional<Book> book = findBook.findByIsbn(anyString());

        assertEquals(Optional.of(bookWithoutIsbn), book);
    }

    @Test
    void findAllByCategoryName_TwoBooksWithGivenCategory() {
        when(repository.findAllByCategoryName(anyString()))
                .thenReturn(Lists.newArrayList(bookWithIsbn, bookWithoutIsbn));

        List<Book> books = findBook.findAllByCategoryName(anyString());
        assertEquals(Sets.newHashSet(bookWithIsbn, bookWithoutIsbn), Sets.newHashSet(books));
    }

    @Test
    void findAllByCategoryName_BookWithGivenCategoryNotExist() {
        when(repository.findAllByCategoryName(anyString()))
                .thenReturn(Lists.newArrayList());

        List<Book> books = findBook.findAllByCategoryName(anyString());

        assertEquals(Lists.newArrayList(), books);
    }
}