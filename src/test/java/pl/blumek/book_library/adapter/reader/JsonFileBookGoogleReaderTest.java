package pl.blumek.book_library.adapter.reader;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.entity.Category;
import pl.blumek.book_library.domain.entity.Language;
import pl.blumek.book_library.domain.entity.Person;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

class JsonFileBookGoogleReaderTest {
    private static final String FILE_NOT_EXISTS = "FILE_NOT_EXISTS";
    private static final String TWO_BOOKS_PATH =
            "google-json-files/two-books.json";
    private static final String TWO_BOOKS_WITH_EXTRA_NODES_PATH =
            "google-json-files/two-books-with-extra-nodes.json";
    private static final String EMPTY_COLLECTION_PATH =
            "google-json-files/empty-collection.json";
    private static final String NO_ITEMS_NODE_PATH =
            "google-json-files/no-items-node.json";

    private static final String FIRST_ID = "FIRST_ID";
    private static final String FIRST_ISBN = "FIRST_ISBN";
    private static final String FIRST_TITLE = "FIRST_TITLE";
    private static final String FIRST_SUBTITLE = "FIRST_SUBTITLE";
    private static final String FIRST_DESCRIPTION = "FIRST_DESCRIPTION";
    private static final int FIRST_PAGE_COUNT = 100;
    private static final double FIRST_AVERAGE_RATING = 4.5;
    private static final String FIRST_THUMBNAIL = "FIRST_THUMBNAIL";
    private static final String FIRST_PREVIEW_LINK = "FIRST_PREVIEW_LINK";
    private static final String FIRST_LANGUAGE = "FIRST_LANGUAGE";
    private static final String FIRST_CATEGORY_NAME = "FIRST_CATEGORY_NAME";
    private static final String SECOND_CATEGORY_NAME = "SECOND_CATEGORY_NAME";
    private static final String FIRST_PUBLISHER = "FIRST_PUBLISHER";
    private static final String FIRST_AUTHOR_FIRST_NAME = "FIRST_AUTHOR_FIRST_NAME";
    private static final String FIRST_AUTHOR_LAST_NAME = "FIRST_AUTHOR_LAST_NAME";
    private static final String SECOND_AUTHOR_LAST_NAME = "SECOND_AUTHOR_LAST_NAME";
    private static final String SECOND_AUTHOR_FIRST_NAME = "SECOND_AUTHOR_FIRST_NAME";

    private static final String SECOND_ID = "SECOND_ID";
    private static final String SECOND_ISBN = "SECOND_ISBN";
    private static final String SECOND_TITLE = "SECOND_TITLE";
    private static final String SECOND_SUBTITLE = "SECOND_SUBTITLE";
    private static final String SECOND_DESCRIPTION = "SECOND_DESCRIPTION";
    private static final int SECOND_PAGE_COUNT = 200;
    private static final double SECOND_AVERAGE_RATING = 3.5;
    private static final String SECOND_THUMBNAIL = "SECOND_THUMBNAIL";
    private static final String SECOND_PREVIEW_LINK = "SECOND_PREVIEW_LINK";
    private static final String SECOND_LANGUAGE = "SECOND_LANGUAGE";
    private static final String SECOND_PUBLISHER = "SECOND_PUBLISHER";

    private JsonFileBookGoogleReader reader;
    private File jsonFile;

    private Book firstBook;
    private Book secondBook;

    @BeforeEach
    void setUp() {
        firstBook = Book.builder()
                .id(FIRST_ID)
                .isbn(FIRST_ISBN)
                .title(FIRST_TITLE)
                .subtitle(FIRST_SUBTITLE)
                .description(FIRST_DESCRIPTION)
                .language(Language.builder()
                        .shortName(FIRST_LANGUAGE)
                        .build())
                .pageCount(FIRST_PAGE_COUNT)
                .averageRating(FIRST_AVERAGE_RATING)
                .thumbnailUrl(FIRST_THUMBNAIL)
                .previewLink(FIRST_PREVIEW_LINK)
                .category(Category.builder()
                        .name(FIRST_CATEGORY_NAME)
                        .build())
                .category(Category.builder()
                        .name(SECOND_CATEGORY_NAME)
                        .build())
                .publisher(FIRST_PUBLISHER)
                .publishedDate(LocalDate.of(2014, 2, 5))
                .author(Person.builder()
                        .firstName(FIRST_AUTHOR_FIRST_NAME)
                        .lastName(FIRST_AUTHOR_LAST_NAME)
                        .build())
                .author(Person.builder()
                        .firstName(SECOND_AUTHOR_FIRST_NAME)
                        .lastName(SECOND_AUTHOR_LAST_NAME)
                        .build())
                .build();

        secondBook = Book.builder()
                .id(SECOND_ID)
                .isbn(SECOND_ISBN)
                .title(SECOND_TITLE)
                .subtitle(SECOND_SUBTITLE)
                .description(SECOND_DESCRIPTION)
                .language(Language.builder()
                        .shortName(SECOND_LANGUAGE)
                        .build())
                .pageCount(SECOND_PAGE_COUNT)
                .averageRating(SECOND_AVERAGE_RATING)
                .thumbnailUrl(SECOND_THUMBNAIL)
                .previewLink(SECOND_PREVIEW_LINK)
                .category(Category.builder()
                        .name(FIRST_CATEGORY_NAME)
                        .build())
                .publisher(SECOND_PUBLISHER)
                .publishedDate(LocalDate.of(2015, 5, 7))
                .author(Person.builder()
                        .firstName(FIRST_AUTHOR_FIRST_NAME)
                        .lastName(FIRST_AUTHOR_LAST_NAME)
                        .build())
                .build();
    }

    @Test
    void readTest_TwoBooks() throws IOException {
        jsonFile = getFile(TWO_BOOKS_PATH);
        reader = new JsonFileBookGoogleReader(jsonFile);

        assertThat(reader.read(), containsInAnyOrder(firstBook, secondBook));
    }

    @SuppressWarnings("ConstantConditions")
    private File getFile(String path) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        return new File(classLoader.getResource(path).getFile());
    }

    @Test
    void readTest_TwoBooks_FileWithExtraNodes() throws IOException {
        jsonFile = getFile(TWO_BOOKS_WITH_EXTRA_NODES_PATH);
        reader = new JsonFileBookGoogleReader(jsonFile);

        assertThat(reader.read(), containsInAnyOrder(firstBook, secondBook));
    }

    @Test
    void readTest_FileNotExists() {
        reader = new JsonFileBookGoogleReader(new File(FILE_NOT_EXISTS));

        assertThrows(IOException.class, () -> reader.read());
    }

    @Test
    void readTest_EmptyCollection() throws IOException {
        jsonFile = getFile(EMPTY_COLLECTION_PATH);
        reader = new JsonFileBookGoogleReader(jsonFile);

        assertIterableEquals(Lists.newArrayList(), reader.read());
    }

    @Test
    void readTest_NoItemsNode() {
        jsonFile = getFile(NO_ITEMS_NODE_PATH);
        reader = new JsonFileBookGoogleReader(jsonFile);

        assertThrows(IOException.class, () -> reader.read());
    }
}