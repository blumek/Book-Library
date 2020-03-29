package pl.blumek.book_library.adapter.average_rating;

import pl.blumek.book_library.domain.port.AverageRating;

import java.util.Collection;

public class ArithmeticMeanAverageRating implements AverageRating {

    @Override
    public double getAverageRatingOf(Collection<Double> ratings) {
        if (ratings.isEmpty())
            throw new IllegalArgumentException("Empty lists passed");

        double sumOfRatings = ratings.stream()
                .mapToDouble(value -> value)
                .sum();

        return sumOfRatings / ratings.size();
    }
}
