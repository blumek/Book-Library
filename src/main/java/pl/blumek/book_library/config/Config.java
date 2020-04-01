package pl.blumek.book_library.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import pl.blumek.book_library.adapter.average_rating.ArithmeticMeanAverageRatingCalculator;
import pl.blumek.book_library.adapter.controller.BookController;
import pl.blumek.book_library.adapter.controller.RatingController;
import pl.blumek.book_library.domain.port.AuthorRepository;
import pl.blumek.book_library.domain.port.AverageRatingCalculator;
import pl.blumek.book_library.domain.port.BookRepository;
import pl.blumek.book_library.usecase.FindAuthorRating;
import pl.blumek.book_library.usecase.FindBook;

@Builder
public class Config {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        return objectMapper;
    }

    public BookController bookController() {
        return new BookController(findBook());
    }

    private FindBook findBook() {
        return new FindBook(bookRepository);
    }

    public RatingController ratingController() {
        return new RatingController(findAuthorRating());
    }

    private FindAuthorRating findAuthorRating() {
        return new FindAuthorRating(authorRepository, bookRepository, averageRatingCalculator());
    }

    private AverageRatingCalculator averageRatingCalculator() {
        return new ArithmeticMeanAverageRatingCalculator();
    }


}
