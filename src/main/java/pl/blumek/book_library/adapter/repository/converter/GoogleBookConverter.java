package pl.blumek.book_library.adapter.repository.converter;

import com.google.api.services.books.model.Volume;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.entity.Category;
import pl.blumek.book_library.domain.entity.Language;
import pl.blumek.book_library.domain.entity.Person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GoogleBookConverter {
    private static final String ISBN_TOKEN = "ISBN_13";

    public static Book toBook(Volume googleBook) {
        Book.BookBuilder bookBuilder = Book.builder();

        Optional<String> id = getId(googleBook);
        id.ifPresent(bookBuilder::id);

        Optional<Volume.VolumeInfo> mainContent = getMainContent(googleBook);
        if (mainContent.isPresent()) {
            Optional<String> isbn = getIsbn(mainContent.get());
            isbn.ifPresent(bookBuilder::isbn);

            Optional<String> title = getTitle(mainContent.get());
            title.ifPresent(bookBuilder::title);

            Optional<String> subtitle = getSubTitle(mainContent.get());
            subtitle.ifPresent(bookBuilder::subtitle);

            Optional<String> publisher = getPublisher(mainContent.get());
            publisher.ifPresent(bookBuilder::publisher);

            Optional<LocalDate> publishedDate = getPublishedDate(mainContent.get());
            publishedDate.ifPresent(bookBuilder::publishedDate);

            Optional<String> description = getDescription(mainContent.get());
            description.ifPresent(bookBuilder::description);

            Optional<Integer> pageCount = getPageCount(mainContent.get());
            pageCount.ifPresent(bookBuilder::pageCount);

            Optional<String> thumbnailUrl = getThumbnailUrl(mainContent.get());
            thumbnailUrl.ifPresent(bookBuilder::thumbnailUrl);

            Optional<Language> language = getLanguage(mainContent.get());
            language.ifPresent(bookBuilder::language);

            Optional<String> previewLink = getPreviewLink(mainContent.get());
            previewLink.ifPresent(bookBuilder::previewLink);

            Optional<Double> averageRating = getAverageRating(mainContent.get());
            averageRating.ifPresent(bookBuilder::averageRating);

            bookBuilder.authors(getAuthors(mainContent.get()));
            bookBuilder.categories(getCategories(mainContent.get()));
        }

        return bookBuilder.build();
    }

    private static Optional<String> getId(Volume googleBook) {
        return Optional.ofNullable(googleBook.getId());
    }

    private static Optional<Volume.VolumeInfo> getMainContent(Volume googleBook) {
        return Optional.ofNullable(googleBook.getVolumeInfo());
    }

    private static Optional<String> getIsbn(Volume.VolumeInfo mainContent) {
        if (mainContent.getIndustryIdentifiers() == null)
            return Optional.empty();

        return mainContent.getIndustryIdentifiers().stream()
                .filter(GoogleBookConverter::isIsbn)
                .findFirst()
                .map(Volume.VolumeInfo.IndustryIdentifiers::getIdentifier);
    }

    private static boolean isIsbn(Volume.VolumeInfo.IndustryIdentifiers industryIdentifiers) {
        return industryIdentifiers.getType().equals(ISBN_TOKEN);
    }

    private static Optional<String> getTitle(Volume.VolumeInfo mainContent) {
        return Optional.ofNullable(mainContent.getTitle());
    }

    private static Optional<String> getSubTitle(Volume.VolumeInfo mainContent) {
        return Optional.ofNullable(mainContent.getSubtitle());
    }

    private static Optional<String> getPublisher(Volume.VolumeInfo mainContent) {
        return Optional.ofNullable(mainContent.getPublisher());
    }

    private static Optional<LocalDate> getPublishedDate(Volume.VolumeInfo mainContent) {
        String date = mainContent.getPublishedDate();
        if (date == null)
            return Optional.empty();

        return Optional.ofNullable(LocalDate.parse(date, getDateFormatter()));
    }

    private static DateTimeFormatter getDateFormatter() {
        return new DateTimeFormatterBuilder()
                    .appendPattern("yyyy")
                    .optionalStart()
                    .appendPattern("-MM")
                    .optionalStart()
                    .appendPattern("-dd")
                    .optionalEnd()
                    .optionalEnd()
                    .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                    .toFormatter();
    }

    private static Optional<String> getDescription(Volume.VolumeInfo mainContent) {
        return Optional.ofNullable(mainContent.getDescription());
    }

    private static Optional<Integer> getPageCount(Volume.VolumeInfo mainContent) {
        return Optional.ofNullable(mainContent.getPageCount());
    }

    private static Optional<String> getThumbnailUrl(Volume.VolumeInfo mainContent) {
        if (mainContent.getImageLinks() == null)
            return Optional.empty();

        return Optional.ofNullable(mainContent.getImageLinks()
                .getThumbnail());
    }

    private static Optional<Language> getLanguage(Volume.VolumeInfo mainContent) {
        if (mainContent.getLanguage() == null)
            return Optional.empty();

        return Optional.of(Language.builder()
                .shortName(mainContent.getLanguage())
                .build());
    }

    private static Optional<String> getPreviewLink(Volume.VolumeInfo mainContent) {
        return Optional.ofNullable(mainContent.getPreviewLink());
    }

    private static Optional<Double> getAverageRating(Volume.VolumeInfo mainContent) {
        return Optional.ofNullable(mainContent.getAverageRating());
    }

    private static Collection<? extends Person> getAuthors(Volume.VolumeInfo mainContent) {
        if (mainContent.getAuthors() == null)
            return Lists.newArrayList();

        return mainContent.getAuthors().stream()
                .filter(authorName -> !isEmpty(authorName))
                .map(AuthorNameConverter::toAuthor)
                .collect(toList());
    }

    private static boolean isEmpty(String authorName) {
        return authorName == null || authorName.isEmpty();
    }

    private static Collection<? extends Category> getCategories(Volume.VolumeInfo mainContent) {
        if (mainContent.getCategories() == null)
            return Lists.newArrayList();

        return mainContent.getCategories().stream()
                .map(GoogleBookConverter::toCategory)
                .collect(toList());
    }

    private static Category toCategory(String categoryName) {
        return Category.builder()
                .name(categoryName)
                .build();
    }
}
