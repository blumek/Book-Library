package pl.blumek.book_library.adapter.repository.converter;

import com.google.api.services.books.model.Volume;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.entity.Category;
import pl.blumek.book_library.domain.entity.Language;
import pl.blumek.book_library.domain.entity.Person;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GoogleBookConverterTest {
    private static final String ID = "ID";
    private static final String ISBN_TOKEN = "ISBN_13";
    private static final String ISBN = "ISBN";
    private static final String TITLE = "TITLE";
    private static final String SUBTITLE = "SUBTITLE";
    private static final String PUBLISHER = "PUBLISHER";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final int PAGE_COUNT = 100;
    private static final String THUMBNAIL_URL = "THUMBNAIL_URL";
    private static final String LANGUAGE = "LANGUAGE";
    private static final String PREVIEW_LINK = "PREVIEW_LINK";
    private static final double AVERAGE_RATING = 4.5;
    private static final String FIRST_AUTHOR_LAST_NAME = "FIRST_AUTHOR_LAST_NAME";
    private static final String FIRST_AUTHOR_FIRST_NAME = "FIRST_AUTHOR_FIRST_NAME";
    private static final String FIRST_AUTHOR = FIRST_AUTHOR_FIRST_NAME + " " + FIRST_AUTHOR_LAST_NAME;
    private static final String SECOND_AUTHOR_FIRST_NAME = "SECOND_AUTHOR_FIRST_NAME";
    private static final String SECOND_AUTHOR_LAST_NAME = "SECOND_AUTHOR_LAST_NAME";
    private static final String SECOND_AUTHOR = SECOND_AUTHOR_FIRST_NAME + " " + SECOND_AUTHOR_LAST_NAME;
    private static final String FIRST_CATEGORY = "FIRST_CATEGORY";
    private static final String SECOND_CATEGORY = "SECOND_CATEGORY";

    @Test
    void toBookTest_OnlyId() {
        Volume volume = new Volume();
        volume.setId(ID);

        Book book = Book.builder()
                .id(ID)
                .build();

        assertEquals(GoogleBookConverter.toBook(volume), book);
    }

    @Test
    void toBookTest_OnlyIsbn() {
        Volume.VolumeInfo.IndustryIdentifiers industryIdentifiers = new Volume.VolumeInfo.IndustryIdentifiers();
        industryIdentifiers.setType(ISBN_TOKEN);
        industryIdentifiers.setIdentifier(ISBN);

        Volume.VolumeInfo volumeInfo = new Volume.VolumeInfo();
        volumeInfo.setIndustryIdentifiers(Lists.newArrayList(industryIdentifiers));

        Volume volume = new Volume();
        volume.setVolumeInfo(volumeInfo);

        Book book = Book.builder()
                .isbn(ISBN)
                .build();

        assertEquals(GoogleBookConverter.toBook(volume), book);
    }

    @Test
    void toBookTest_OnlyTitle() {
        Volume.VolumeInfo volumeInfo = new Volume.VolumeInfo();
        volumeInfo.setTitle(TITLE);

        Volume volume = new Volume();
        volume.setVolumeInfo(volumeInfo);

        Book book = Book.builder()
                .title(TITLE)
                .build();

        assertEquals(GoogleBookConverter.toBook(volume), book);
    }

    @Test
    void toBookTest_OnlySubtitle() {
        Volume.VolumeInfo volumeInfo = new Volume.VolumeInfo();
        volumeInfo.setSubtitle(SUBTITLE);

        Volume volume = new Volume();
        volume.setVolumeInfo(volumeInfo);

        Book book = Book.builder()
                .subtitle(SUBTITLE)
                .build();

        assertEquals(GoogleBookConverter.toBook(volume), book);
    }

    @Test
    void toBookTest_OnlyPublisher() {
        Volume.VolumeInfo volumeInfo = new Volume.VolumeInfo();
        volumeInfo.setPublisher(PUBLISHER);

        Volume volume = new Volume();
        volume.setVolumeInfo(volumeInfo);

        Book book = Book.builder()
                .publisher(PUBLISHER)
                .build();

        assertEquals(GoogleBookConverter.toBook(volume), book);
    }

    @Test
    void toBookTest_OnlyPublishedDate() {
        Volume.VolumeInfo volumeInfo = new Volume.VolumeInfo();
        volumeInfo.setPublishedDate("2014-02-03");

        Volume volume = new Volume();
        volume.setVolumeInfo(volumeInfo);

        Book book = Book.builder()
                .publishedDate(LocalDate.of(2014, 2, 3))
                .build();

        assertEquals(GoogleBookConverter.toBook(volume), book);
    }

    @Test
    void toBookTest_OnlyDescription() {
        Volume.VolumeInfo volumeInfo = new Volume.VolumeInfo();
        volumeInfo.setDescription(DESCRIPTION);

        Volume volume = new Volume();
        volume.setVolumeInfo(volumeInfo);

        Book book = Book.builder()
                .description(DESCRIPTION)
                .build();

        assertEquals(GoogleBookConverter.toBook(volume), book);
    }

    @Test
    void toBookTest_OnlyPageCount() {
        Volume.VolumeInfo volumeInfo = new Volume.VolumeInfo();
        volumeInfo.setPageCount(PAGE_COUNT);

        Volume volume = new Volume();
        volume.setVolumeInfo(volumeInfo);

        Book book = Book.builder()
                .pageCount(PAGE_COUNT)
                .build();

        assertEquals(GoogleBookConverter.toBook(volume), book);
    }

    @Test
    void toBookTest_OnlyThumbnailUrl() {
        Volume.VolumeInfo.ImageLinks imageLinks = new Volume.VolumeInfo.ImageLinks();
        imageLinks.setThumbnail(THUMBNAIL_URL);

        Volume.VolumeInfo volumeInfo = new Volume.VolumeInfo();
        volumeInfo.setImageLinks(imageLinks);

        Volume volume = new Volume();
        volume.setVolumeInfo(volumeInfo);

        Book book = Book.builder()
                .thumbnailUrl(THUMBNAIL_URL)
                .build();

        assertEquals(GoogleBookConverter.toBook(volume), book);
    }

    @Test
    void toBookTest_OnlyLanguage() {
        Volume.VolumeInfo volumeInfo = new Volume.VolumeInfo();
        volumeInfo.setLanguage(LANGUAGE);

        Volume volume = new Volume();
        volume.setVolumeInfo(volumeInfo);

        Book book = Book.builder()
                .language(Language.builder()
                        .shortName(LANGUAGE)
                        .build())
                .build();

        assertEquals(GoogleBookConverter.toBook(volume), book);
    }

    @Test
    void toBookTest_OnlyPreviewLink() {
        Volume.VolumeInfo volumeInfo = new Volume.VolumeInfo();
        volumeInfo.setPreviewLink(PREVIEW_LINK);

        Volume volume = new Volume();
        volume.setVolumeInfo(volumeInfo);

        Book book = Book.builder()
                .previewLink(PREVIEW_LINK)
                .build();

        assertEquals(GoogleBookConverter.toBook(volume), book);
    }

    @Test
    void toBookTest_OnlyAverageRating() {
        Volume.VolumeInfo volumeInfo = new Volume.VolumeInfo();
        volumeInfo.setAverageRating(AVERAGE_RATING);

        Volume volume = new Volume();
        volume.setVolumeInfo(volumeInfo);

        Book book = Book.builder()
                .averageRating(AVERAGE_RATING)
                .build();

        assertEquals(GoogleBookConverter.toBook(volume), book);
    }

    @Test
    void toBookTest_OnlyTwoAuthors() {
        Person firstAuthor = Person.builder()
                .firstName(FIRST_AUTHOR_FIRST_NAME)
                .lastName(FIRST_AUTHOR_LAST_NAME)
                .build();

        Person secondAuthor = Person.builder()
                .firstName(SECOND_AUTHOR_FIRST_NAME)
                .lastName(SECOND_AUTHOR_LAST_NAME)
                .build();

        Volume.VolumeInfo volumeInfo = new Volume.VolumeInfo();
        volumeInfo.setAuthors(Lists.newArrayList(FIRST_AUTHOR, SECOND_AUTHOR));

        Volume volume = new Volume();
        volume.setVolumeInfo(volumeInfo);

        Book book = Book.builder()
                .author(firstAuthor)
                .author(secondAuthor)
                .build();

        assertEquals(GoogleBookConverter.toBook(volume), book);
    }

    @Test
    void toBookTest_OnlyTwoCategories() {
        Category firstCategory = Category.builder()
                .name(FIRST_CATEGORY)
                .build();

        Category secondCategory = Category.builder()
                .name(SECOND_CATEGORY)
                .build();

        Volume.VolumeInfo volumeInfo = new Volume.VolumeInfo();
        volumeInfo.setCategories(Lists.newArrayList(FIRST_CATEGORY, SECOND_CATEGORY));

        Volume volume = new Volume();
        volume.setVolumeInfo(volumeInfo);

        Book book = Book.builder()
                .category(firstCategory)
                .category(secondCategory)
                .build();

        assertEquals(GoogleBookConverter.toBook(volume), book);
    }
}