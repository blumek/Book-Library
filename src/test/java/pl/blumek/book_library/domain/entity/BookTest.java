package pl.blumek.book_library.domain.entity;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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

    private Book book;
    private Book expectedBook;

    @BeforeEach
    void setUp() {
        Language language = Language.builder()
                .id("LANGUAGE_ID")
                .fullName("LANGUAGE_FULL_NAME")
                .shortName("LANGUAGE_SHORT_NAME")
                .build();

        Category firstCategory = Category.builder()
                .id("CATEGORY_ID_1")
                .name("CATEGORY_NAME_1")
                .build();

        Category secondCategory = Category.builder()
                .id("CATEGORY_ID_2")
                .name("CATEGORY_NAME_2")
                .build();

        Person publisher = Person.builder()
                .id("PERSON_ID")
                .firstName("FIRST_NAME")
                .lastName("LAST_NAME")
                .build();

        Person firstAuthor = Person.builder()
                .id("PERSON_ID_1")
                .firstName("FIRST_NAME_1")
                .lastName("LAST_NAME_1")
                .build();

        Person secondAuthor = Person.builder()
                .id("PERSON_ID_2")
                .firstName("FIRST_NAME_2")
                .lastName("LAST_NAME_2")
                .build();

        LocalDate now = LocalDate.now();

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
                .publishedDate(now)
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
                 .categories(
                         Lists.newArrayList(firstCategory, secondCategory)
                 )
                 .publisher(publisher)
                 .publishedDate(now)
                 .authors(
                         Lists.newArrayList(firstAuthor, secondAuthor)
                 )
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