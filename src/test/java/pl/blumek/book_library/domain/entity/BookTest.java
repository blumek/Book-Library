package pl.blumek.book_library.domain.entity;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
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
    private static final String PUBLISHER_FIRST_NAME = "PUBLISHER_FIRST_NAME";
    private static final String PUBLISHER_LAST_NAME = "PUBLISHER_LAST_NAME";
    private static final String FIRST_AUTHOR_FIRST_NAME = "FIRST_AUTHOR_FIRST_NAME";
    private static final String FIRST_AUTHOR_LAST_NAME = "FIRST_AUTHOR_LAST_NAME";
    private static final String SECOND_AUTHOR_LAST_NAME = "SECOND_AUTHOR_LAST_NAME";
    private static final String SECOND_AUTHOR_FIRST_NAME = "SECOND_AUTHOR_FIRST_NAME";

    private Book book;
    private Book expectedBook;

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

        Person publisher = Person.builder()
                .firstName(PUBLISHER_FIRST_NAME)
                .lastName(PUBLISHER_LAST_NAME)
                .build();

        Person firstAuthor = Person.builder()
                .firstName(FIRST_AUTHOR_FIRST_NAME)
                .lastName(FIRST_AUTHOR_LAST_NAME)
                .build();

        Person secondAuthor = Person.builder()
                .firstName(SECOND_AUTHOR_FIRST_NAME)
                .lastName(SECOND_AUTHOR_LAST_NAME)
                .build();

        LocalDateTime date = LocalDateTime
                .of(2000, 10, 10, 10, 0, 0);

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
                .publisher(publisher)
                .publishedDate(date)
                .author(firstAuthor)
                .author(secondAuthor)
                .build();

        expectedBook = Book.builder()
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
                .categories(Lists.newArrayList(firstCategory, secondCategory))
                .publisher(publisher)
                .publishedDate(date)
                .authors(Lists.newArrayList(firstAuthor, secondAuthor))
                .build();
    }

    @Test
    void equalsTest_equalBooks() {
        assertEquals(expectedBook, book);
    }

    @Test
    void hashCodeTest_equalBooks() {
        assertEquals(expectedBook.hashCode(), book.hashCode());
    }
}