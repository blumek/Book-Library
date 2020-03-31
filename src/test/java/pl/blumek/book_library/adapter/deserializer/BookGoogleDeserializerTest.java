package pl.blumek.book_library.adapter.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.entity.Category;
import pl.blumek.book_library.domain.entity.Language;
import pl.blumek.book_library.domain.entity.Person;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookGoogleDeserializerTest {
    private static final String ID = "ID";
    private static final String TITLE = "TITLE";
    private static final String SUBTITLE = "SUBTITLE";
    private static final String AUTHOR_FIRST_NAME = "AUTHOR_FIRST_NAME";
    private static final String AUTHOR_LAST_NAME = "AUTHOR_LAST_NAME";
    private static final String SECOND_AUTHOR_FIRST_NAME = "SECOND_AUTHOR_FIRST_NAME";
    private static final String SECOND_AUTHOR_LAST_NAME = "SECOND_AUTHOR_LAST_NAME";
    private static final String PUBLISHER = "PUBLISHER";
    private static final String DATE = "2004-04-01";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String ISBN = "ISBN";
    private static final int PAGE_COUNT = 100;
    private static final String CATEGORY_NAME = "CATEGORY_NAME";
    private static final String SECOND_CATEGORY_NAME = "SECOND_CATEGORY_NAME";
    private static final double AVERAGE_RATING = 3.5;
    private static final String THUMBNAIL = "THUMBNAIL";
    private static final String LANGUAGE_SHORT_NAME = "LANGUAGE_SHORT_NAME";
    private static final String PREVIEW_LINK = "PREVIEW_LINK";
    private static final String NO_ISBN = "NO_ISBN";
    private static final String ISBN_TOKEN_TYPE = "ISBN_13";

    private String json;
    private ObjectMapper objectMapper;
    private Book expectedBook;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        BookGoogleDeserializer deserializer = new BookGoogleDeserializer(Book.class);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Book.class, deserializer);
        objectMapper.registerModule(module);

        json = "{\n" +
                "   \"id\": \"" + ID + "\",\n" +
                "   \"volumeInfo\": {\n" +
                "    \"title\": \"" + TITLE + "\",\n" +
                "    \"subtitle\": \"" + SUBTITLE + "\",\n" +
                "    \"authors\": [\n" +
                "     \"" + AUTHOR_FIRST_NAME + " " + AUTHOR_LAST_NAME + "\"\n" +
                "    ],\n" +
                "    \"publisher\": \"" + PUBLISHER + "\",\n" +
                "    \"publishedDate\": \"" + DATE + "\",\n" +
                "    \"description\": \"" + DESCRIPTION + "\",\n" +
                "    \"industryIdentifiers\": [\n" +
                "     {\n" +
                "      \"type\": \"" + ISBN_TOKEN_TYPE + "\",\n" +
                "      \"identifier\": \"" + ISBN + "\"\n" +
                "     }\n" +
                "    ],\n" +
                "    \"pageCount\": " + PAGE_COUNT + ",\n" +
                "    \"averageRating\": " + AVERAGE_RATING + ",\n" +
                "    \"categories\": [\n" +
                "     \"" + CATEGORY_NAME + "\"\n" +
                "    ],\n" +
                "    \"imageLinks\": {\n" +
                "     \"thumbnail\": \"" + THUMBNAIL + "\"\n" +
                "    },\n" +
                "    \"language\": \"" + LANGUAGE_SHORT_NAME + "\",\n" +
                "    \"previewLink\": \"" + PREVIEW_LINK + "\"\n" +
                "   }\n" +
                "}";

        LocalDate publishedDate = LocalDate.of(2004, 4, 1);

        expectedBook = Book.builder()
                .id(ID)
                .isbn(ISBN)
                .title(TITLE)
                .subtitle(SUBTITLE)
                .publisher(PUBLISHER)
                .publishedDate(publishedDate)
                .description(DESCRIPTION)
                .pageCount(PAGE_COUNT)
                .thumbnailUrl(THUMBNAIL)
                .language(Language.builder()
                        .shortName(LANGUAGE_SHORT_NAME)
                        .build())
                .previewLink(PREVIEW_LINK)
                .averageRating(AVERAGE_RATING)
                .author(Person.builder()
                        .firstName(AUTHOR_FIRST_NAME)
                        .lastName(AUTHOR_LAST_NAME)
                        .build())
                .category(Category.builder()
                        .name(CATEGORY_NAME)
                        .build())
                .build();
    }

    @Test
    void deserializeTest_fullObject() throws JsonProcessingException {
        Book book = objectMapper.readValue(json, Book.class);
        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_onlyIdentifier() throws JsonProcessingException {
        json = "{\n" +
                "   \"id\": \"" + ID + "\"\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .id(ID)
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_noIsbnAvailable() throws JsonProcessingException {
        json = "{\n" +
                "  \"volumeInfo\": {\n" +
                "      \"industryIdentifiers\": [\n" +
                "       {\n" +
                "        \"type\": \"" + NO_ISBN + "\",\n" +
                "        \"identifier\": \"" + NO_ISBN + "\"\n" +
                "       }\n" +
                "      ]\n" +
                "  }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_IsbnAvailable() throws JsonProcessingException {
        json = "{\n" +
                "  \"volumeInfo\": {\n" +
                "      \"industryIdentifiers\": [\n" +
                "       {\n" +
                "        \"type\": \"" + ISBN_TOKEN_TYPE + "\",\n" +
                "        \"identifier\": \"" + ISBN + "\"\n" +
                "       }\n" +
                "      ]\n" +
                "  }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .isbn(ISBN)
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_NoIdentifiersAvailable() throws JsonProcessingException {
        json = "{\n" +
                "  \"volumeInfo\": {\n" +
                "  }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_onlyTitle() throws JsonProcessingException {
        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"title\": \"" + TITLE + "\"\n" +
                "   }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .title(TITLE)
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_onlySubtitle() throws JsonProcessingException {
        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"subtitle\": \"" + SUBTITLE + "\"\n" +
                "   }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .subtitle(SUBTITLE)
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_onlyPublisher() throws JsonProcessingException {
        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"publisher\": \"" + PUBLISHER + "\"\n" +
                "   }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .publisher(PUBLISHER)
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_publishedDateFullDate() throws JsonProcessingException {
        json = "{\n" +
                "  \"volumeInfo\": {\n" +
                "    \"publishedDate\": \"2004-04-05\"\n" +
                "   }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .publishedDate(LocalDate.of(2004, 4, 5))
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_publishedDateWithoutDays() throws JsonProcessingException {
        json = "{\n" +
                "  \"volumeInfo\": {\n" +
                "    \"publishedDate\": \"2004-04\"\n" +
                "   }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .publishedDate(LocalDate.of(2004, 4, 1))
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_publishedDateOnlyYear() throws JsonProcessingException {
        json = "{\n" +
                "  \"volumeInfo\": {\n" +
                "    \"publishedDate\": \"2004\"\n" +
                "   }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .publishedDate(LocalDate.of(2004, 1, 1))
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_onlyDescription() throws JsonProcessingException {
        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"description\": \"" + DESCRIPTION + "\"\n" +
                "   }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .description(DESCRIPTION)
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_onlyPageCount() throws JsonProcessingException {
        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"pageCount\": " + PAGE_COUNT + "\n" +
                "   }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .pageCount(PAGE_COUNT)
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_onlyThumbnailUrl() throws JsonProcessingException {
        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"imageLinks\": {\n" +
                "     \"thumbnail\": \"" + THUMBNAIL + "\"\n" +
                "    }\n" +
                "   }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .thumbnailUrl(THUMBNAIL)
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_onlyLanguage() throws JsonProcessingException {
        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"language\": \"" + LANGUAGE_SHORT_NAME + "\"\n" +
                "   }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .language(Language.builder()
                        .shortName(LANGUAGE_SHORT_NAME)
                        .build())
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_onlyPreviewLink() throws JsonProcessingException {
        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"previewLink\": \"" + PREVIEW_LINK + "\"\n" +
                "   }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .previewLink(PREVIEW_LINK)
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_onlyAverageRating() throws JsonProcessingException {
        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"averageRating\": " + AVERAGE_RATING + "\n" +
                "   }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .averageRating(AVERAGE_RATING)
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_onlyTwoAuthors() throws JsonProcessingException {
        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"authors\": [\n" +
                "     \"" + AUTHOR_FIRST_NAME + " " + AUTHOR_LAST_NAME + "\",\n" +
                "     \"" + SECOND_AUTHOR_FIRST_NAME + " " + SECOND_AUTHOR_LAST_NAME + "\" \n" +
                "    ]\n" +
                "   }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .author(Person.builder()
                        .firstName(AUTHOR_FIRST_NAME)
                        .lastName(AUTHOR_LAST_NAME)
                        .build())
                .author(Person.builder()
                        .firstName(SECOND_AUTHOR_FIRST_NAME)
                        .lastName(SECOND_AUTHOR_LAST_NAME)
                        .build())
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_onlyAuthorName() throws JsonProcessingException {
        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"authors\": [\n" +
                "     \" " + AUTHOR_FIRST_NAME + " \"\n" +
                "    ]\n" +
                "   }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .author(Person.builder()
                        .firstName(AUTHOR_FIRST_NAME)
                        .build())
                .build();

        assertEquals(expectedBook, book);
    }

    @Test
    void deserializeTest_onlyTwoCategories() throws JsonProcessingException {
        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"categories\": [\n" +
                "     \"" + CATEGORY_NAME + "\",\n" +
                "     \"" + SECOND_CATEGORY_NAME + "\"\n" +
                "    ]\n" +
                "   }\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        expectedBook = Book.builder()
                .category(Category.builder()
                        .name(CATEGORY_NAME)
                        .build())
                .category(Category.builder()
                        .name(SECOND_CATEGORY_NAME)
                        .build())
                .build();

        assertEquals(expectedBook, book);
    }
}