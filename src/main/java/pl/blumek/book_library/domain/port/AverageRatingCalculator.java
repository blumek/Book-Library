package pl.blumek.book_library.domain.port;


import java.util.Collection;

public interface AverageRatingCalculator {
    double calculate(Collection<Double> ratings);
}
