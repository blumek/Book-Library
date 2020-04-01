package pl.blumek.book_library.adapter.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.domain.entity.Person;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorsArrayFromBookGoogleDeserializerTest {
    private static final String FIRST_AUTHOR_FIRST_NAME = "FIRST_AUTHOR_FIRST_NAME";
    private static final String FIRST_AUTHOR_LAST_NAME = "FIRST_AUTHOR_LAST_NAME";
    private static final String SECOND_AUTHOR_FIRST_NAME = "SECOND_AUTHOR_FIRST_NAME";
    private static final String SECOND_AUTHOR_LAST_NAME = "SECOND_AUTHOR_LAST_NAME";

    private String json;
    private ObjectMapper objectMapper;

    private Person firstAuthor;
    private Person secondAuthor;
    private Person authorWithOnlyFirstName;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        AuthorsArrayFromBookGoogleDeserializer deserializer = new AuthorsArrayFromBookGoogleDeserializer(List.class);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(List.class, deserializer);
        objectMapper.registerModule(module);

        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"authors\": [\n" +
                "     \"" + FIRST_AUTHOR_FIRST_NAME + " " + FIRST_AUTHOR_LAST_NAME + "\",\n" +
                "     \"" + SECOND_AUTHOR_FIRST_NAME + " " + SECOND_AUTHOR_LAST_NAME + "\"\n" +
                "    ]\n" +
                "   }\n" +
                "}";

        firstAuthor = Person.builder()
                .firstName(FIRST_AUTHOR_FIRST_NAME)
                .lastName(FIRST_AUTHOR_LAST_NAME)
                .build();

        secondAuthor = Person.builder()
                .firstName(SECOND_AUTHOR_FIRST_NAME)
                .lastName(SECOND_AUTHOR_LAST_NAME)
                .build();

        authorWithOnlyFirstName = Person.builder()
                .firstName(FIRST_AUTHOR_FIRST_NAME)
                .build();
    }

    @Test
    void deserializeTest_TwoAuthors() throws JsonProcessingException {
        JavaType authorList = objectMapper.getTypeFactory().constructCollectionType(List.class, Person.class);
        List<Person> authors = objectMapper.readValue(json, authorList);

        assertEquals(Sets.newHashSet(firstAuthor, secondAuthor), Sets.newHashSet(authors));
    }

    @Test
    void deserializeTest_EmptyCollection() throws JsonProcessingException {
        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"authors\": [\n" +
                "    \n" +
                "    ]\n" +
                "   }\n" +
                "}";

        JavaType authorList = objectMapper.getTypeFactory().constructCollectionType(List.class, Person.class);
        List<Person> authors = objectMapper.readValue(json, authorList);

        assertEquals(Lists.newArrayList(), authors);
    }

    @Test
    void deserializeTest_NoCollection() throws JsonProcessingException {
        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "   }\n" +
                "}";

        JavaType authorList = objectMapper.getTypeFactory().constructCollectionType(List.class, Person.class);
        List<Person> authors = objectMapper.readValue(json, authorList);

        assertEquals(Lists.newArrayList(), authors);
    }

    @Test
    void deserializeTest_AuthorWithOnlyFirstName() throws JsonProcessingException {
        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"authors\": [\n" +
                "     \"" + FIRST_AUTHOR_FIRST_NAME + " \"\n" +
                "    ]\n" +
                "   }\n" +
                "}";

        JavaType authorList = objectMapper.getTypeFactory().constructCollectionType(List.class, Person.class);
        List<Person> authors = objectMapper.readValue(json, authorList);

        assertEquals(Lists.newArrayList(authorWithOnlyFirstName), authors);
    }

    @Test
    void deserializeTest_InvalidJson() {
        json = "{\n" +
                "   \"volumeInfo\": {\n" +
                "    \"authors\": [\n" +
                "     \"" + FIRST_AUTHOR_FIRST_NAME+ " " + FIRST_AUTHOR_LAST_NAME + "\",\n" +
                "    ]\n" +
                "   }\n" +
                "}";

        JavaType authorList = objectMapper.getTypeFactory().constructCollectionType(List.class, Person.class);

        assertThrows(JsonProcessingException.class, () -> objectMapper.readValue(json, authorList));
    }
}