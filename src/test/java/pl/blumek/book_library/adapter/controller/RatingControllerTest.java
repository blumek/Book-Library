package pl.blumek.book_library.adapter.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.adapter.controller.model.AuthorRatingWeb;
import pl.blumek.book_library.domain.entity.Person;
import pl.blumek.book_library.usecase.FindAuthorRating;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingControllerTest {
    private static final String FIRST_AUTHOR_FIRST_NAME = "FIRST_AUTHOR_FIRST_NAME";
    private static final String FIRST_AUTHOR_LAST_NAME = "FIRST_AUTHOR_LAST_NAME";
    private static final String SECOND_AUTHOR_FIRST_NAME = "SECOND_AUTHOR_FIRST_NAME";
    private static final String SECOND_AUTHOR_LAST_NAME = "SECOND_AUTHOR_LAST_NAME";
    private static final double FIRST_AUTHOR_AVERAGE_RATING = 4.0;
    private static final double SECOND_AUTHOR_AVERAGE_RATING = 5.0;

    private FindAuthorRating findAuthorRating;
    private RatingController ratingController;

    private Map<Person, Double> ratings;
    private AuthorRatingWeb firstAuthorRating;
    private AuthorRatingWeb secondAuthorRating;

    @BeforeEach
    void setUp() {
        Person firstAuthor = Person.builder()
                .firstName(FIRST_AUTHOR_FIRST_NAME)
                .lastName(FIRST_AUTHOR_LAST_NAME)
                .build();

        Person secondAuthor = Person.builder()
                .firstName(SECOND_AUTHOR_FIRST_NAME)
                .lastName(SECOND_AUTHOR_LAST_NAME)
                .build();

        ratings = Maps.newHashMap();
        ratings.put(firstAuthor, FIRST_AUTHOR_AVERAGE_RATING);
        ratings.put(secondAuthor, SECOND_AUTHOR_AVERAGE_RATING);

        firstAuthorRating = AuthorRatingWeb.builder()
                .author(firstAuthor.getFullName())
                .averageRating(FIRST_AUTHOR_AVERAGE_RATING)
                .build();

        secondAuthorRating = AuthorRatingWeb.builder()
                .author(secondAuthor.getFullName())
                .averageRating(SECOND_AUTHOR_AVERAGE_RATING)
                .build();

        findAuthorRating = mock(FindAuthorRating.class);
        ratingController = new RatingController(findAuthorRating);
    }

    @Test
    void findAllAuthorsRatingsTest_NoAuthorsRatingsAvailable() {
        when(findAuthorRating.findAllAuthorRatings())
                .thenReturn(Maps.newHashMap());

        assertIterableEquals(Lists.newArrayList(), ratingController.findAllAuthorsRatings());
    }

    @Test
    void findAllAuthorsRatingsTest_TwoAuthorsRatings() {
        when(findAuthorRating.findAllAuthorRatings())
                .thenReturn(ratings);

        assertIterableEquals(Lists.newArrayList(secondAuthorRating, firstAuthorRating),
                ratingController.findAllAuthorsRatings());
    }
}