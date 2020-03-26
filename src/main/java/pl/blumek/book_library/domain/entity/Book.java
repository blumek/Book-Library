package pl.blumek.book_library.domain.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Value
class Book {
    String id;
    String isbn;
    String title;
    String subtitle;
    Person publisher;
    LocalDate publishedDate;
    String description;
    int pageCount;
    String thumbnailUrl;
    Language language;
    String previewLink;
    double averageRating;
    @Singular List<Person> authors;
    @Singular List<Category> categories;
}
