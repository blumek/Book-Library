package pl.blumek.book_library.adapter.controller.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorRatingWebTest {
    private static final String AUTHOR = "AUTHOR";
    private static final String ANOTHER_AUTHOR = "ANOTHER_AUTHOR";
    private static final double AVERAGE_RATING = 3.5;
    private static final int ANOTHER_AVERAGE_RATING = 1;

    private AuthorRatingWeb authorRatingWeb;
    private AuthorRatingWeb expectedAuthorRatingWeb;
    private AuthorRatingWeb anotherAuthorRatingWeb;

    @BeforeEach
    void setUp() {
        authorRatingWeb = AuthorRatingWeb.builder()
                .author(AUTHOR)
                .averageRating(AVERAGE_RATING)
                .build();

        expectedAuthorRatingWeb = AuthorRatingWeb.builder()
                .author(AUTHOR)
                .averageRating(AVERAGE_RATING)
                .build();

        anotherAuthorRatingWeb = AuthorRatingWeb.builder()
                .author(ANOTHER_AUTHOR)
                .averageRating(ANOTHER_AVERAGE_RATING)
                .build();
    }

    @Test
    void equalsTest_TwoEqualAuthorRatings() {
        assertEquals(expectedAuthorRatingWeb, authorRatingWeb);
    }

    @Test
    void equalsTest_NotEqualAuthorRatings() {
        assertNotEquals(anotherAuthorRatingWeb, authorRatingWeb);
    }
}