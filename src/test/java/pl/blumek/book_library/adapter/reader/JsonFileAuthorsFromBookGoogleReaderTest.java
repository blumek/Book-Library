package pl.blumek.book_library.adapter.reader;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.domain.entity.Person;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

class JsonFileAuthorsFromBookGoogleReaderTest {
    private static final String FILE_NOT_EXISTS = "FILE_NOT_EXISTS";
    private static final String TWO_BOOKS_PATH =
            "google-json-files/two-books.json";
    private static final String THREE_AUTHORS_PATH =
            "google-json-files/three-authors.json";
    private static final String NO_AUTHORS_PATH =
            "google-json-files/no-authors.json";

    private static final String FIRST_AUTHOR_FIRST_NAME = "FIRST_AUTHOR_FIRST_NAME";
    private static final String FIRST_AUTHOR_LAST_NAME = "FIRST_AUTHOR_LAST_NAME";
    private static final String SECOND_AUTHOR_LAST_NAME = "SECOND_AUTHOR_LAST_NAME";
    private static final String SECOND_AUTHOR_FIRST_NAME = "SECOND_AUTHOR_FIRST_NAME";
    private static final String THIRD_AUTHOR_FIRST_NAME = "THIRD_AUTHOR_FIRST_NAME";
    private static final String THIRD_AUTHOR_LAST_NAME = "THIRD_AUTHOR_LAST_NAME";

    private File jsonFile;
    private JsonFileAuthorsFromBookGoogleReader reader;

    private Person firstAuthor;
    private Person secondAuthor;
    private Person thirdAuthor;

    @BeforeEach
    void setUp() {
        firstAuthor = Person.builder()
                .firstName(FIRST_AUTHOR_FIRST_NAME)
                .lastName(FIRST_AUTHOR_LAST_NAME)
                .build();

        secondAuthor = Person.builder()
                .firstName(SECOND_AUTHOR_FIRST_NAME)
                .lastName(SECOND_AUTHOR_LAST_NAME)
                .build();

        thirdAuthor = Person.builder()
                .firstName(THIRD_AUTHOR_FIRST_NAME)
                .lastName(THIRD_AUTHOR_LAST_NAME)
                .build();
    }

    @SuppressWarnings("ConstantConditions")
    private File getFile(String path) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        return new File(classLoader.getResource(path).getFile());
    }

    @Test
    void readTest_TwoAuthors_OneTwoTimes_ShouldOmitOne() throws IOException {
        jsonFile = getFile(TWO_BOOKS_PATH);
        reader = new JsonFileAuthorsFromBookGoogleReader(jsonFile);

        assertThat(reader.read(), containsInAnyOrder(firstAuthor, secondAuthor));
    }

    @Test
    void readTest_ThreeAuthors() throws IOException {
        jsonFile = getFile(THREE_AUTHORS_PATH);
        reader = new JsonFileAuthorsFromBookGoogleReader(jsonFile);

        assertThat(reader.read(), containsInAnyOrder(firstAuthor, secondAuthor, thirdAuthor));
    }

    @Test
    void readTest_NoAuthorsAvailable() throws IOException {
        jsonFile = getFile(NO_AUTHORS_PATH);
        reader = new JsonFileAuthorsFromBookGoogleReader(jsonFile);

        assertIterableEquals(Lists.newArrayList(), reader.read());
    }

    @Test
    void readTest_FileNotExists() {
        reader = new JsonFileAuthorsFromBookGoogleReader(new File(FILE_NOT_EXISTS));

        assertThrows(IOException.class, () -> reader.read());
    }
}