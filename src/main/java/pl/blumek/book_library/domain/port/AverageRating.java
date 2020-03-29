package pl.blumek.book_library.domain.port;


import java.util.Collection;

public interface AverageRating {
    double getAverageRatingOf(Collection<Double> ratings);
}
