package pl.blumek.book_library.application.spring_app.controller;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.adapter.controller.RatingController;
import pl.blumek.book_library.adapter.controller.model.AuthorRatingWeb;

import static io.restassured.http.ContentType.*;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.*;


class SpringRatingControllerTest {
    private static final String FIRST_AUTHOR = "FIRST_AUTHOR";
    private static final String SECOND_AUTHOR = "SECOND_AUTHOR";

    private RatingController ratingController;
    private SpringRatingController springRatingController;

    @BeforeEach
    public void setUp() {
        ratingController = mock(RatingController.class);
        springRatingController = new SpringRatingController(ratingController);
    }

    @Test
    void findAllAuthorsRatingsTest_NoAuthorsRatingsAvailable() {
        when(ratingController.findAllAuthorsRatings())
                .thenReturn(Lists.newArrayList());


        given()
                .standaloneSetup(springRatingController)
        .when()
                .get("/ratings/authors")
        .then()
                .contentType(JSON)
                .status(OK)
                .body(is(equalTo("[]")));
    }

    @Test
    void findAllAuthorsRatingsTest_TwoAuthorsRatingsAvailable() {
        AuthorRatingWeb firstAuthorRating = AuthorRatingWeb.builder()
                .author(FIRST_AUTHOR)
                .averageRating(4.5)
                .build();

        AuthorRatingWeb secondAuthorRating = AuthorRatingWeb.builder()
                .author(SECOND_AUTHOR)
                .averageRating(3.5)
                .build();

        when(ratingController.findAllAuthorsRatings())
                .thenReturn(Lists.newArrayList(firstAuthorRating, secondAuthorRating));

        AuthorRatingWeb[] authorsRatings = given()
                .standaloneSetup(springRatingController)
        .when()
                .get("/ratings/authors")
        .then()
                .contentType(JSON)
                .status(OK)
                .extract()
                .as(AuthorRatingWeb[].class);

        assertThat(Lists.newArrayList(authorsRatings), containsInAnyOrder(firstAuthorRating, secondAuthorRating));
    }
}