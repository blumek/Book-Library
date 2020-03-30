package pl.blumek.book_library.adapter.controller.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Value
public class AuthorRatingWeb {
    String author;
    double averageRating;
}
