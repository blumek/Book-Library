package pl.blumek.book_library.adapter.average_rating;

import pl.blumek.book_library.domain.port.AverageRatingCalculator;

import java.util.Collection;

public class ArithmeticMeanAverageRatingCalculator implements AverageRatingCalculator {

    @Override
    public double calculate(Collection<Double> ratings) {
        if (ratings.isEmpty())
            throw new IllegalArgumentException("Empty lists passed");

        double sumOfRatings = ratings.stream()
                .mapToDouble(value -> value)
                .sum();

        return sumOfRatings / ratings.size();
    }
}
