package pl.blumek.book_library.adapter.controller.model;

import lombok.*;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.entity.Category;
import pl.blumek.book_library.domain.entity.Language;
import pl.blumek.book_library.domain.entity.Person;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class BookWeb {
    private String isbn;
    private String title;
    private String subtitle;
    private String publisher;
    private long publishedDate;
    private String description;
    private int pageCount;
    private String thumbnailUrl;
    private String language;
    private String previewLink;
    private double averageRating;
    @Singular
    private List<String> authors;
    @Singular
    private List<String> categories;

    public static BookWeb from(Book book) {
        return BookWeb.builder()
                .isbn(getIsbn(book))
                .title(book.getTitle())
                .subtitle(book.getSubtitle())
                .publisher(book.getPublisher())
                .publishedDate(getPublishedDate(book.getPublishedDate()))
                .description(book.getDescription())
                .pageCount(book.getPageCount())
                .thumbnailUrl(book.getThumbnailUrl())
                .language(getLanguage(book.getLanguage()))
                .previewLink(book.getPreviewLink())
                .averageRating(book.getAverageRating())
                .authors(getAuthors(book.getAuthors()))
                .categories(getCategories(book.getCategories()))
                .build();
    }

    private static String getIsbn(Book book) {
        return book.getIsbn() == null ? book.getId() : book.getIsbn();
    }

    private static long getPublishedDate(LocalDate publishedDate) {
        return publishedDate == null ? 0 : publishedDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    private static String getLanguage(Language language) {
        return language == null ? null : language.getShortName();
    }

    private static Collection<? extends String> getAuthors(List<Person> authors) {
        return authors.stream()
                .map(BookWeb::getAuthorName)
                .collect(toList());
    }

    private static String getAuthorName(Person author) {
        return author.getFirstName() + " " + author.getLastName();
    }

    private static Collection<? extends String> getCategories(List<Category> categories) {
        return categories.stream()
                .map(Category::getName)
                .collect(toList());
    }
}
