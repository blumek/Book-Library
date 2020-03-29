package pl.blumek.book_library.adapter.average_rating;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithmeticMeanAverageRatingTest {

    private ArithmeticMeanAverageRating arithmeticMeanAverageRating;

    @BeforeEach
    void setUp() {
        arithmeticMeanAverageRating = new ArithmeticMeanAverageRating();
    }

    @Test
    void getAverageRatingOfTest_fourRatings() {
        double averageRating = arithmeticMeanAverageRating.getAverageRatingOf(
                Lists.newArrayList(1.0, 2.0, 3.0, 4.0));

        assertEquals(2.5, averageRating);
    }

    @Test
    void getAverageRatingOfTest_emptyList() {
        assertThrows(IllegalArgumentException.class,
                () ->  arithmeticMeanAverageRating.getAverageRatingOf(Lists.newArrayList()));
    }
}