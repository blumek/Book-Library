package pl.blumek.book_library.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTest {
    private static final String LANGUAGE_ID = "1";
    private static final String LANGUAGE_FULL_NAME = "ENGLISH";
    private static final String LANGUAGE_SHORT_NAME = "EN";

    private Language language;
    private Language expectedLanguage;

    @BeforeEach
    void setUp() {
        language = Language.builder()
                .id(LANGUAGE_ID)
                .fullName(LANGUAGE_FULL_NAME)
                .shortName(LANGUAGE_SHORT_NAME)
                .build();

        expectedLanguage = Language.builder()
                .id(LANGUAGE_ID)
                .fullName(LANGUAGE_FULL_NAME)
                .shortName(LANGUAGE_SHORT_NAME)
                .build();
    }

    @Test
    void equalsTest_equalLanguages() {
        assertEquals(expectedLanguage, language);
    }

    @Test
    void hashCodeTest_equalLanguages() {
        assertEquals(expectedLanguage.hashCode(), language.hashCode());
    }
}