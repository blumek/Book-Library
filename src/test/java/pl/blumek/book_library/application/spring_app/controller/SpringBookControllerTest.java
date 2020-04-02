package pl.blumek.book_library.application.spring_app.controller;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.adapter.controller.BookController;
import pl.blumek.book_library.adapter.controller.model.BookWeb;
import pl.blumek.book_library.domain.exception.BookNotFoundException;

import static io.restassured.http.ContentType.*;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.*;


class SpringBookControllerTest {
    private static final String FIRST_ISBN = "FIRST_ISBN";
    private static final String ANOTHER_ISBN = "ANOTHER_ISBN";
    private static final String NOT_EXISTING_ISBN = "NOT_EXISTING_ISBN";
    private static final String NOT_EXISTING_CATEGORY = "NOT_EXISTING_CATEGORY";
    private static final String FIRST_CATEGORY = "FIRST_CATEGORY";

    private BookController bookController;
    private SpringBookController springBookController;

    @BeforeEach
    void setUp() {
        bookController = mock(BookController.class);
        springBookController = new SpringBookController(bookController);
    }

    @Test
    void findByIsbnTest_getBookWithGivenIsbn() {
        BookWeb bookWeb = BookWeb.builder()
                .isbn(FIRST_ISBN)
                .build();

        when(bookController.findByIsbn(FIRST_ISBN))
                .thenReturn(bookWeb);

        given()
                .standaloneSetup(springBookController)
        .when()
                .get("/books/" + FIRST_ISBN)
        .then()
                .status(OK)
                .contentType(JSON)
                .assertThat()
                .body("isbn", equalTo(FIRST_ISBN));
    }

    @Test
    void findByIsbnTest_noBookWithGivenIsbnOrId() {
        when(bookController.findByIsbn(anyString()))
                .thenThrow(BookNotFoundException.class);

        given()
                .standaloneSetup(springBookController)
        .when()
                .get("/books/" + NOT_EXISTING_ISBN)
        .then()
                .statusCode(NOT_FOUND.value());
    }

    @Test
    void findAllTest_NoBooksWithGivenCategory() {
        when(bookController.findAllByCategoryName(anyString()))
                .thenReturn(Lists.newArrayList());

        given()
                .standaloneSetup(springBookController)
        .when()
                .get("/books?category=" + NOT_EXISTING_CATEGORY)
        .then()
                .contentType(JSON)
                .status(OK)
                .body(is(equalTo("[]")));
    }

    @Test
    void findAllTest_TwoBooksWithGivenCategory() {
        BookWeb firstBookWeb = BookWeb.builder()
                .isbn(FIRST_ISBN)
                .category(FIRST_CATEGORY)
                .build();

        BookWeb secondBookWeb = BookWeb.builder()
                .isbn(ANOTHER_ISBN)
                .category(FIRST_CATEGORY)
                .build();

        when(bookController.findAllByCategoryName(anyString()))
                .thenReturn(Lists.newArrayList(firstBookWeb, secondBookWeb));

        BookWeb[] books = given()
                .standaloneSetup(springBookController)
        .when()
                .get("/books?category=" + FIRST_CATEGORY)
        .then()
                .contentType(JSON)
                .status(OK)
                .extract()
                .as(BookWeb[].class);

        assertThat(Lists.newArrayList(books), containsInAnyOrder(firstBookWeb, secondBookWeb));
    }
}