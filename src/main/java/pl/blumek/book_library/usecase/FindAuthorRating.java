package pl.blumek.book_library.usecase;

import com.google.common.collect.Maps;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.entity.Person;
import pl.blumek.book_library.domain.port.AuthorRepository;
import pl.blumek.book_library.domain.port.AverageRatingCalculator;
import pl.blumek.book_library.domain.port.BookRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;


public final class FindAuthorRating {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AverageRatingCalculator averageRatingCalculator;

    public FindAuthorRating(AuthorRepository authorRepository, BookRepository bookRepository,
                            AverageRatingCalculator averageRatingCalculator) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.averageRatingCalculator = averageRatingCalculator;
    }

    public Map<Person, Double> findAllAuthorRatings() {
        HashMap<Person, Double> ratings = Maps.newHashMap();

        List<Person> authors = getAllAuthors();
        for (Person author : authors) {
            OptionalDouble averageRating = getAverageRatingOfAuthor(author);
            averageRating.ifPresent(rating -> ratings.put(author, rating));
        }

        return ratings;
    }

    private List<Person> getAllAuthors() {
        return authorRepository.findAll();
    }

    private OptionalDouble getAverageRatingOfAuthor(Person author) {
        List<Book> authorBooks = getAllAuthorBooks(author);
        if (authorBooks.isEmpty())
            return OptionalDouble.empty();

        List<Double> ratingsOfBooks = getAllBookAverageRatings(authorBooks);

        return calculate(ratingsOfBooks);
    }

    private OptionalDouble calculate(List<Double> ratingsOfBooks) {
        return averageRatingCalculator.calculate(ratingsOfBooks);
    }

    private List<Book> getAllAuthorBooks(Person author) {
        return bookRepository.findAllByAuthorName(author.getFullName());
    }

    private List<Double> getAllBookAverageRatings(List<Book> authorBooks) {
        return authorBooks.stream()
                .map(Book::getAverageRating)
                .filter(rating -> rating > 0)
                .collect(Collectors.toList());
    }
}
