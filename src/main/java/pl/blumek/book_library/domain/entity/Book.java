package pl.blumek.book_library.domain.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Value
public class Book {
    String id;
    String isbn;
    String title;
    String subtitle;
    Person publisher;
    LocalDateTime publishedDate;
    String description;
    int pageCount;
    String thumbnailUrl;
    Language language;
    String previewLink;
    double averageRating;
    @Singular List<Person> authors;
    @Singular List<Category> categories;
}
