package pl.blumek.book_library.adapter.controller.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.entity.Category;
import pl.blumek.book_library.domain.entity.Language;
import pl.blumek.book_library.domain.entity.Person;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookWebTest {
    private static final String BOOK_ID = "BOOK_ID";
    private static final String BOOK_ISBN = "BOOK_ISBN";
    private static final String BOOK_TITLE = "BOOK_TITLE";
    private static final String BOOK_SUBTITLE = "BOOK_SUBTITLE";
    private static final String BOOK_DESCRIPTION = "BOOK_DESCRIPTION";
    private static final int BOOK_PAGE_COUNT = 100;
    private static final double BOOK_AVERAGE_RATING = 4.5;
    private static final String BOOK_THUMBNAIL_URL = "BOOK_THUMBNAIL_URL";
    private static final String BOOK_PREVIEW_LINK = "BOOK_PREVIEW_LINK";
    private static final String LANGUAGE_FULL_NAME = "LANGUAGE_FULL_NAME";
    private static final String LANGUAGE_SHORT_NAME = "LANGUAGE_SHORT_NAME";
    private static final String FIRST_CATEGORY_NAME = "FIRST_CATEGORY_NAME";
    private static final String SECOND_CATEGORY_NAME = "SECOND_CATEGORY_NAME";
    private static final String PUBLISHER = "PUBLISHER";
    private static final long PUBLISHED_DATE = 1314568800000L;
    private static final String FIRST_AUTHOR_FIRST_NAME = "FIRST_AUTHOR_FIRST_NAME";
    private static final String FIRST_AUTHOR_LAST_NAME = "FIRST_AUTHOR_LAST_NAME";
    private static final String FIRST_AUTHOR = FIRST_AUTHOR_FIRST_NAME + " " + FIRST_AUTHOR_LAST_NAME;
    private static final String SECOND_AUTHOR_LAST_NAME = "SECOND_AUTHOR_LAST_NAME";
    private static final String SECOND_AUTHOR_FIRST_NAME = "SECOND_AUTHOR_FIRST_NAME";
    private static final String SECOND_AUTHOR = SECOND_AUTHOR_FIRST_NAME + " " + SECOND_AUTHOR_LAST_NAME;


    private Book book;
    private BookWeb expectedBook;

    private Book bookNotFull;
    private BookWeb expectedBookNotFull;

    @BeforeEach
    void setUp() {
        Language language = Language.builder()
                .fullName(LANGUAGE_FULL_NAME)
                .shortName(LANGUAGE_SHORT_NAME)
                .build();

        Category firstCategory = Category.builder()
                .name(FIRST_CATEGORY_NAME)
                .build();

        Category secondCategory = Category.builder()
                .name(SECOND_CATEGORY_NAME)
                .build();

        Person firstAuthor = Person.builder()
                .firstName(FIRST_AUTHOR_FIRST_NAME)
                .lastName(FIRST_AUTHOR_LAST_NAME)
                .build();

        Person secondAuthor = Person.builder()
                .firstName(SECOND_AUTHOR_FIRST_NAME)
                .lastName(SECOND_AUTHOR_LAST_NAME)
                .build();

        LocalDate date = LocalDate.of(2011, 8, 29);

        book = Book.builder()
                .id(BOOK_ID)
                .isbn(BOOK_ISBN)
                .title(BOOK_TITLE)
                .subtitle(BOOK_SUBTITLE)
                .description(BOOK_DESCRIPTION)
                .language(language)
                .pageCount(BOOK_PAGE_COUNT)
                .averageRating(BOOK_AVERAGE_RATING)
                .thumbnailUrl(BOOK_THUMBNAIL_URL)
                .previewLink(BOOK_PREVIEW_LINK)
                .category(firstCategory)
                .category(secondCategory)
                .publisher(PUBLISHER)
                .publishedDate(date)
                .author(firstAuthor)
                .author(secondAuthor)
                .build();

        expectedBook = BookWeb.builder()
                .isbn(BOOK_ISBN)
                .title(BOOK_TITLE)
                .subtitle(BOOK_SUBTITLE)
                .description(BOOK_DESCRIPTION)
                .language(LANGUAGE_SHORT_NAME)
                .pageCount(BOOK_PAGE_COUNT)
                .averageRating(BOOK_AVERAGE_RATING)
                .thumbnailUrl(BOOK_THUMBNAIL_URL)
                .previewLink(BOOK_PREVIEW_LINK)
                .category(FIRST_CATEGORY_NAME)
                .category(SECOND_CATEGORY_NAME)
                .publisher(PUBLISHER)
                .publishedDate(PUBLISHED_DATE)
                .author(FIRST_AUTHOR)
                .author(SECOND_AUTHOR)
                .build();

        bookNotFull = Book.builder()
                .isbn(BOOK_ISBN)
                .title(BOOK_TITLE)
                .build();

        expectedBookNotFull = BookWeb.builder()
                .isbn(BOOK_ISBN)
                .title(BOOK_TITLE)
                .build();
    }

    @Test
    void fromTest_fullObject() {
        assertEquals(expectedBook, BookWeb.from(book));
    }

    @Test
    void fromTest_NotFullObject() {
        assertEquals(expectedBookNotFull, BookWeb.from(bookNotFull));
    }
}