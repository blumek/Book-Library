package pl.blumek.book_library.adapter.average_rating;

import pl.blumek.book_library.domain.port.AverageRatingCalculator;

import java.util.Collection;
import java.util.OptionalDouble;

public class ArithmeticMeanAverageRatingCalculator implements AverageRatingCalculator {

    @Override
    public OptionalDouble calculate(Collection<Double> ratings) {
        if (ratings.isEmpty())
            return OptionalDouble.empty();

        double sumOfRatings = ratings.stream()
                .mapToDouble(value -> value)
                .sum();

        return OptionalDouble.of(sumOfRatings / ratings.size());
    }
}
