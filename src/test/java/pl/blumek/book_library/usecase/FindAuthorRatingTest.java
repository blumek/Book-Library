package pl.blumek.book_library.usecase;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.entity.Person;
import pl.blumek.book_library.domain.port.AuthorRepository;
import pl.blumek.book_library.domain.port.AverageRatingCalculator;
import pl.blumek.book_library.domain.port.BookRepository;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindAuthorRatingTest {
    private static final String FIRST_AUTHOR_FIRST_NAME = "FIRST_AUTHOR_FIRST_NAME";
    private static final String FIRST_AUTHOR_LAST_NAME = "FIRST_AUTHOR_LAST_NAME";
    private static final String SECOND_AUTHOR_FIRST_NAME = "SECOND_AUTHOR_FIRST_NAME";
    private static final String SECOND_AUTHOR_LAST_NAME = "SECOND_AUTHOR_LAST_NAME";
    private static final double FIRST_BOOK_AVERAGE_RATING = 4;
    private static final double SECOND_BOOK_AVERAGE_RATING = 5;
    private static final double THIRD_BOOK_AVERAGE_RATING = 3;
    private static final double FIRST_AUTHOR_AVERAGE_RATING =
            (FIRST_BOOK_AVERAGE_RATING + SECOND_BOOK_AVERAGE_RATING) / 2;
    private static final double SECOND_AUTHOR_AVERAGE_RATING = THIRD_BOOK_AVERAGE_RATING;

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private FindAuthorRating findAuthorRating;
    private AverageRatingCalculator averageRatingCalculator;

    private Person firstAuthor;
    private Person secondAuthor;

    private Book firstBook;
    private Book secondBook;
    private Book thirdBook;
    private Book bookWithoutAverageRating;


    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        authorRepository = mock(AuthorRepository.class);
        averageRatingCalculator = mock(AverageRatingCalculator.class);

        findAuthorRating = new FindAuthorRating(authorRepository, bookRepository, averageRatingCalculator);

        firstAuthor = Person.builder()
                .firstName(FIRST_AUTHOR_FIRST_NAME)
                .lastName(FIRST_AUTHOR_LAST_NAME)
                .build();

        secondAuthor = Person.builder()
                .firstName(SECOND_AUTHOR_FIRST_NAME)
                .lastName(SECOND_AUTHOR_LAST_NAME)
                .build();

        firstBook = Book.builder()
                .averageRating(FIRST_BOOK_AVERAGE_RATING)
                .author(firstAuthor)
                .build();

        secondBook = Book.builder()
                .averageRating(SECOND_BOOK_AVERAGE_RATING)
                .author(firstAuthor)
                .build();

        thirdBook = Book.builder()
                .averageRating(THIRD_BOOK_AVERAGE_RATING)
                .author(secondAuthor)
                .build();

        bookWithoutAverageRating = Book.builder()
                .author(secondAuthor)
                .build();
    }

    @Test
    void findAllAuthorRatingsTest_TwoAuthors() {
        when(authorRepository.findAll())
                .thenReturn(Lists.newArrayList(firstAuthor, secondAuthor));

        when(bookRepository.findAllByAuthorName(firstAuthor.getFullName()))
                .thenReturn(Lists.newArrayList(firstBook, secondBook));

        when(bookRepository.findAllByAuthorName(secondAuthor.getFullName()))
                .thenReturn(Lists.newArrayList(thirdBook));

        when(averageRatingCalculator.calculate(Lists.newArrayList(FIRST_BOOK_AVERAGE_RATING, SECOND_BOOK_AVERAGE_RATING)))
                .thenReturn(FIRST_AUTHOR_AVERAGE_RATING);

        when(averageRatingCalculator.calculate(Lists.newArrayList(THIRD_BOOK_AVERAGE_RATING)))
                .thenReturn(SECOND_AUTHOR_AVERAGE_RATING);

        Map<Person, Double> authorRatings = findAuthorRating.findAllAuthorRatings();

        Map<Person, Double> expectedAuthorRatings = Maps.newHashMap();
        expectedAuthorRatings.put(firstAuthor, FIRST_AUTHOR_AVERAGE_RATING);
        expectedAuthorRatings.put(secondAuthor, SECOND_AUTHOR_AVERAGE_RATING);

        assertTrue(Maps.difference(expectedAuthorRatings, authorRatings).areEqual());
    }

    @Test
    void findAllAuthorRatingsTest_AuthorWithBookWithoutAverageRating() {
        when(authorRepository.findAll())
                .thenReturn(Lists.newArrayList(secondAuthor));

        when(bookRepository.findAllByAuthorName(secondAuthor.getFullName()))
                .thenReturn(Lists.newArrayList(thirdBook, bookWithoutAverageRating));

        when(averageRatingCalculator.calculate(Lists.newArrayList(THIRD_BOOK_AVERAGE_RATING)))
                .thenReturn(SECOND_AUTHOR_AVERAGE_RATING);

        Map<Person, Double> authorRatings = findAuthorRating.findAllAuthorRatings();

        Map<Person, Double> expectedAuthorRatings = Maps.newHashMap();
        expectedAuthorRatings.put(secondAuthor, SECOND_AUTHOR_AVERAGE_RATING);

        assertTrue(Maps.difference(expectedAuthorRatings, authorRatings).areEqual());
    }
}