package pl.blumek.book_library.adapter.controller.model;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class AuthorRatingWeb {
    String author;
    double averageRating;
}
