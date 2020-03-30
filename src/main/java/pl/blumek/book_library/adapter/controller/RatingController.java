package pl.blumek.book_library.adapter.controller;

import com.google.common.collect.Lists;
import pl.blumek.book_library.adapter.controller.model.AuthorRatingWeb;
import pl.blumek.book_library.domain.entity.Person;
import pl.blumek.book_library.usecase.FindAuthorRating;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class RatingController {
    private final FindAuthorRating findAuthorRating;

    public RatingController(FindAuthorRating findAuthorRating) {
        this.findAuthorRating = findAuthorRating;
    }

    public List<AuthorRatingWeb> findAllAuthorsRatings() {
        Map<Person, Double> ratingsOfAuthors = findAuthorRating.findAllAuthorRatings();
        return getAuthorsRatings(ratingsOfAuthors).stream()
                .sorted(Comparator.comparingDouble(AuthorRatingWeb::getAverageRating).reversed())
                .collect(toList());
    }

    private List<AuthorRatingWeb> getAuthorsRatings(Map<Person, Double> ratingsOfAuthors) {
        List<AuthorRatingWeb> authorsRatings = Lists.newArrayList();
        for (Map.Entry<Person, Double> ratingEntry : ratingsOfAuthors.entrySet()) {
            authorsRatings.add(getAuthorRatingWebOf(ratingEntry));
        }
        return authorsRatings;
    }

    private AuthorRatingWeb getAuthorRatingWebOf(Map.Entry<Person, Double> ratingEntry) {
        Person author = ratingEntry.getKey();
        double rating = ratingEntry.getValue();
        return AuthorRatingWeb.builder()
                .author(author.getFullName())
                .averageRating(rating)
                .build();
    }

}
