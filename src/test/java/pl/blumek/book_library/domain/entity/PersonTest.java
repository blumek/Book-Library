package pl.blumek.book_library.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    private static final String PERSON_ID = "PERSON_ID";
    private static final String PERSON_FIRST_NAME = "PERSON_FIRST_NAME";
    private static final String PERSON_LAST_NAME = "PERSON_LAST_NAME";

    private Person person;
    private Person expectedPerson;

    @BeforeEach
    void setUp() {
        person = Person.builder()
                .id(PERSON_ID)
                .firstName(PERSON_FIRST_NAME)
                .lastName(PERSON_LAST_NAME)
                .build();

        expectedPerson = Person.builder()
                .id(PERSON_ID)
                .firstName(PERSON_FIRST_NAME)
                .lastName(PERSON_LAST_NAME)
                .build();
    }

    @Test
    void equalsTest_equalPeople() {
        assertEquals(expectedPerson, person);
    }

    @Test
    void hashCodeTest_equalPeople() {
        assertEquals(expectedPerson.hashCode(), person.hashCode());
    }
}