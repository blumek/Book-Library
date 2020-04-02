package pl.blumek.book_library.adapter.repository.converter;

import org.junit.jupiter.api.Test;
import pl.blumek.book_library.domain.entity.Person;

import static org.junit.jupiter.api.Assertions.*;

class AuthorNameConverterTest {
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";

    @Test
    void toAuthorTest_FirstNameAndLastName() {
        Person author = Person.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        assertEquals(author, AuthorNameConverter.toAuthor("FIRST_NAME LAST_NAME"));
    }

    @Test
    void toAuthorTest_OnlyFirstName() {
        Person author = Person.builder()
                .firstName(FIRST_NAME)
                .build();

        assertEquals(author, AuthorNameConverter.toAuthor("FIRST_NAME"));
    }

    @Test
    void toAuthorTest_OnlyFirstNameWithSpaces() {
        Person author = Person.builder()
                .firstName(FIRST_NAME)
                .build();

        assertEquals(author, AuthorNameConverter.toAuthor("  FIRST_NAME  "));
    }
}