package pl.blumek.book_library.domain.port;


import java.util.Collection;
import java.util.OptionalDouble;

public interface AverageRatingCalculator {
    OptionalDouble calculate(Collection<Double> ratings);
}
