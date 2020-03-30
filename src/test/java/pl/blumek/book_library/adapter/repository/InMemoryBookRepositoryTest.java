package pl.blumek.book_library.adapter.repository;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.entity.Category;
import pl.blumek.book_library.domain.entity.Person;
import pl.blumek.book_library.domain.port.IdGenerator;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InMemoryBookRepositoryTest {
    private static final String SECOND_BOOK_ID = "SECOND_BOOK_ID";
    private static final String FIRST_BOOK_ID = "FIRST_BOOK_ID";
    private static final String NOT_EXISTING_ID = "NOT_EXISTING_ID";
    private static final String NOT_EXISTING_ISBN = "NOT_EXISTING_ISBN";
    private static final String FIRST_BOOK_ISBN = "FIRST_BOOK_ISBN";
    private static final String CATEGORY_NAME = "CATEGORY_NAME";
    private static final String NOT_EXISTING_CATEGORY_NAME = "NOT_EXISTING_CATEGORY_NAME";
    private static final String THIRD_BOOK_ISBN = "THIRD_BOOK_ISBN";
    private static final String AUTHOR_FIRST_NAME = "AUTHOR_FIRST_NAME";
    private static final String AUTHOR_LAST_NAME = "AUTHOR_LAST_NAME";
    private static final String AUTHOR_FULL_NAME = AUTHOR_FIRST_NAME + " " + AUTHOR_LAST_NAME;
    private static final String NOT_EXISTING_AUTHOR_FULL_NAME = "NOT_EXISTING_AUTHOR_FULL_NAME";
    private static final String GENERATED_ID = "GENERATED_ID";

    private IdGenerator idGenerator;
    private InMemoryBookRepository repository;

    private Book firstBook;
    private Book secondBook;
    private Book thirdBook;

    @BeforeEach
    void setUp() {
        Category category = Category.builder()
                .name(CATEGORY_NAME)
                .build();

        Person author = Person.builder()
                .firstName(AUTHOR_FIRST_NAME)
                .lastName(AUTHOR_LAST_NAME)
                .build();

        firstBook = Book.builder()
                .id(FIRST_BOOK_ID)
                .isbn(FIRST_BOOK_ISBN)
                .category(category)
                .author(author)
                .build();

        secondBook = Book.builder()
                .id(SECOND_BOOK_ID)
                .category(category)
                .author(author)
                .build();

        thirdBook = Book.builder()
                .isbn(THIRD_BOOK_ISBN)
                .build();

        idGenerator = mock(IdGenerator.class);
        repository = new InMemoryBookRepository(idGenerator, firstBook, secondBook, thirdBook);
    }

    @Test
    void varargsConstructorTest_TwoBooks() {
        repository = new InMemoryBookRepository(idGenerator, firstBook, secondBook);

        assertEquals(Optional.of(firstBook), repository.findById(FIRST_BOOK_ID));
        assertEquals(Optional.of(secondBook), repository.findById(SECOND_BOOK_ID));
    }

    @Test
    void listConstructorTest_TwoBooks() {
        repository = new InMemoryBookRepository(idGenerator, Lists.newArrayList(firstBook, secondBook));

        assertEquals(Optional.of(firstBook), repository.findById(FIRST_BOOK_ID));
        assertEquals(Optional.of(secondBook), repository.findById(SECOND_BOOK_ID));
    }

    @Test
    void streamConstructorTest_TwoBooks() {
        repository = new InMemoryBookRepository(idGenerator, Stream.of(firstBook, secondBook));

        assertEquals(Optional.of(firstBook), repository.findById(FIRST_BOOK_ID));
        assertEquals(Optional.of(secondBook), repository.findById(SECOND_BOOK_ID));
    }

    @Test
    void constructorTest_BookWithNoId_ShouldGenerateOne() {
        when(idGenerator.generate())
                .thenReturn(GENERATED_ID);

        repository = new InMemoryBookRepository(idGenerator, firstBook, secondBook, thirdBook);

        Optional<Book> book = repository.findById(GENERATED_ID);

        Book expectedBook = thirdBook.toBuilder()
                .id(GENERATED_ID)
                .build();

        assertEquals(Optional.of(expectedBook), book);
    }

    @Test
    void findByIdTest_BookWithGivenIdNotExists() {
        assertEquals(Optional.empty(), repository.findById(NOT_EXISTING_ID));
    }

    @Test
    void findByIdTest_BookWithGivenIdExists() {
        assertEquals(Optional.of(firstBook), repository.findById(FIRST_BOOK_ID));
    }

    @Test
    void findByIsbnTest_BookWithGivenIsbnNotExists() {
        assertEquals(Optional.empty(), repository.findByIsbn(NOT_EXISTING_ISBN));
    }

    @Test
    void findByIsbnTest_BookWithGivenIsbnExists() {
        assertEquals(Optional.of(firstBook), repository.findByIsbn(FIRST_BOOK_ISBN));
    }

    @Test
    void findAllByCategoryNameTest_NoBooksGivenCategoryName() {
        assertEquals(Lists.newArrayList(), repository.findAllByCategoryName(NOT_EXISTING_CATEGORY_NAME));
    }

    @Test
    void findAllByCategoryNameTest_TwoWithGivenCategoryName() {
        assertEquals(Lists.newArrayList(firstBook, secondBook), repository.findAllByCategoryName(CATEGORY_NAME));
    }

    @Test
    void findAllByAuthorNameTest_NoBooksWithGivenAuthorName() {
        assertEquals(Lists.newArrayList(), repository.findAllByAuthorName(NOT_EXISTING_AUTHOR_FULL_NAME));
    }

    @Test
    void findAllByAuthorNameTest_TwoWithGivenAuthorName() {
        assertEquals(Lists.newArrayList(firstBook, secondBook), repository.findAllByAuthorName(AUTHOR_FULL_NAME));
    }
}