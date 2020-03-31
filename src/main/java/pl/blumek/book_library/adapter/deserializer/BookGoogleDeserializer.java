package pl.blumek.book_library.adapter.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.common.collect.Lists;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.entity.Category;
import pl.blumek.book_library.domain.entity.Language;
import pl.blumek.book_library.domain.entity.Person;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;

public class BookGoogleDeserializer extends StdDeserializer<Book> {
    private static final String ID_NODE_NAME = "id";
    private static final String CONTENT_NODE_NAME = "volumeInfo";
    private static final String IDENTIFIERS_NODE_NAME = "industryIdentifiers";
    private static final String IDENTIFIER_TYPE_NODE_NAME = "type";
    private static final String IDENTIFIER_NODE_NAME = "identifier";
    private static final String ISBN = "ISBN_13";
    private static final String TITLE_NODE_NAME = "title";
    private static final String SUBTITLE_NODE_NAME = "subtitle";
    private static final String PUBLISHER_NODE_NAME = "publisher";
    private static final String PUBLISHED_DATE_NODE_NAME = "publishedDate";
    private static final String DESCRIPTION_NODE_NAME = "description";
    private static final String PAGE_COUNT_NODE_NAME = "pageCount";
    private static final String IMAGE_LINKS_NODE_NAME = "imageLinks";
    private static final String THUMBNAIL = "thumbnail";
    private static final String LANGUAGE_NODE_NAME = "language";
    private static final String PREVIEW_LINK_NODE_NAME = "previewLink";
    private static final String AVERAGE_RATING_NODE_NAME = "averageRating";
    private static final String AUTHORS_NODE_NAME = "authors";
    private static final String CATEGORIES_NODE_NAME = "categories";

    public BookGoogleDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Book deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode rootNode =  jsonParser.getCodec().readTree(jsonParser);
        return getDeserializedBook(rootNode);
    }
    
    private Book getDeserializedBook(JsonNode rootNode) {
        Book.BookBuilder bookBuilder = Book.builder();

        Optional<String> id = getId(rootNode);
        id.ifPresent(bookBuilder::id);

        Optional<JsonNode> contentNode = getContentNode(rootNode);
        if (contentNode.isPresent()) {
            Optional<String> isbn = getIsbn(contentNode.get());
            isbn.ifPresent(bookBuilder::isbn);

            Optional<String> title = getTitle(contentNode.get());
            title.ifPresent(bookBuilder::title);

            Optional<String> subtitle = getSubtitle(contentNode.get());
            subtitle.ifPresent(bookBuilder::subtitle);

            Optional<Person> publisher = getPublisher(contentNode.get());
            publisher.ifPresent(bookBuilder::publisher);

            Optional<LocalDateTime> date = getPublishedDate(contentNode.get());
            date.ifPresent(bookBuilder::publishedDate);

            Optional<String> description = getDescription(contentNode.get());
            description.ifPresent(bookBuilder::description);

            Optional<Integer> pageCount = getPageCount(contentNode.get());
            pageCount.ifPresent(bookBuilder::pageCount);

            Optional<String> thumbnailUrl = getThumbnailUrl(contentNode.get());
            thumbnailUrl.ifPresent(bookBuilder::thumbnailUrl);

            Optional<Language> language = getLanguage(contentNode.get());
            language.ifPresent(bookBuilder::language);

            Optional<String> previewLink = getPreviewLink(contentNode.get());
            previewLink.ifPresent(bookBuilder::previewLink);

            Optional<Double> averageRating = getAverageRating(contentNode.get());
            averageRating.ifPresent(bookBuilder::averageRating);

            bookBuilder.authors(getAuthors(contentNode.get()));
            bookBuilder.categories(getCategories(contentNode.get()));
        }

        return bookBuilder.build();
    }

    private Optional<String> getId(JsonNode node) {
        JsonNode idNode = node.get(ID_NODE_NAME);
        if (idNode == null)
            return Optional.empty();

        return Optional.of(idNode.textValue());
    }

    private Optional<JsonNode> getContentNode(JsonNode node) {
        return Optional.ofNullable(node.get(CONTENT_NODE_NAME));
    }

    private Optional<String> getIsbn(JsonNode contentNode) {
        JsonNode identifiersNode = contentNode.get(IDENTIFIERS_NODE_NAME);
        if (identifiersNode == null)
            return Optional.empty();

        return findIsbn(identifiersNode);
    }

    private Optional<String> findIsbn(JsonNode identifiersNode) {
        for (JsonNode identifierNode : identifiersNode) {
            JsonNode idTypeNode = identifierNode.get(IDENTIFIER_TYPE_NODE_NAME);
            if (!isIsbn(idTypeNode))
                continue;

            JsonNode idNode = identifierNode.get(IDENTIFIER_NODE_NAME);
            if (idNode != null)
                return Optional.ofNullable(idNode.textValue());
        }

        return Optional.empty();
    }

    private boolean isIsbn(JsonNode idTypeNode) {
        return idTypeNode != null && idTypeNode.textValue().equals(ISBN);
    }

    private Optional<String> getTitle(JsonNode contentNode) {
        JsonNode titleNode = contentNode.get(TITLE_NODE_NAME);
        if (titleNode == null)
            return Optional.empty();

        return Optional.of(titleNode.textValue());
    }

    private Optional<String> getSubtitle(JsonNode contentNode) {
        JsonNode subtitleNode = contentNode.get(SUBTITLE_NODE_NAME);
        if (subtitleNode == null)
            return Optional.empty();

        return Optional.of(subtitleNode.textValue());
    }

    private Optional<Person> getPublisher(JsonNode contentNode) {
        JsonNode publisherNode = contentNode.get(PUBLISHER_NODE_NAME);
        if (publisherNode == null)
            return Optional.empty();

        return Optional.of(Person.builder()
                .firstName(publisherNode.textValue())
                .build());
    }

    private Optional<LocalDateTime> getPublishedDate(JsonNode contentNode) {
        JsonNode publishedDateNode = contentNode.get(PUBLISHED_DATE_NODE_NAME);
        if (publishedDateNode == null)
            return Optional.empty();

        return getDateFrom(publishedDateNode.textValue());
    }

    private Optional<LocalDateTime> getDateFrom(String publishedDateNode) {
        if (publishedDateNode == null)
            return Optional.empty();

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy")
                .optionalStart()
                    .appendPattern("-MM")
                    .optionalStart()
                        .appendPattern("-dd")
                    .optionalEnd()
                .optionalEnd()
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();

        return Optional.of(LocalDateTime.parse(publishedDateNode, formatter));
    }

    private Optional<String> getDescription(JsonNode contentNode) {
        JsonNode descriptionNode = contentNode.get(DESCRIPTION_NODE_NAME);
        if (descriptionNode == null)
            return Optional.empty();

        return Optional.of(descriptionNode.textValue());
    }

    private Optional<Integer> getPageCount(JsonNode contentNode) {
        JsonNode pageCountNode = contentNode.get(PAGE_COUNT_NODE_NAME);
        if (pageCountNode == null)
            return Optional.empty();

        return Optional.of(pageCountNode.intValue());
    }

    private Optional<String> getThumbnailUrl(JsonNode contentNode) {
        JsonNode imageLinksNode = contentNode.get(IMAGE_LINKS_NODE_NAME);
        if (imageLinksNode == null)
            return Optional.empty();

        JsonNode thumbnailNode = imageLinksNode.get(THUMBNAIL);
        if (thumbnailNode == null)
            return Optional.empty();

        return Optional.ofNullable(thumbnailNode.textValue());
    }

    private Optional<Language> getLanguage(JsonNode contentNode) {
        JsonNode languageNode = contentNode.get(LANGUAGE_NODE_NAME);
        if (languageNode == null)
            return Optional.empty();

        return Optional.of(Language.builder()
                .shortName(languageNode.textValue())
                .build());
    }

    private Optional<String> getPreviewLink(JsonNode contentNode) {
        JsonNode previewLinkNode = contentNode.get(PREVIEW_LINK_NODE_NAME);
        if (previewLinkNode == null)
            return Optional.empty();

        return Optional.of(previewLinkNode.textValue());
    }

    private Optional<Double> getAverageRating(JsonNode contentNode) {
        JsonNode averageRatingNode = contentNode.get(AVERAGE_RATING_NODE_NAME);
        if (averageRatingNode == null)
            return Optional.empty();

        return Optional.of(averageRatingNode.doubleValue());
    }

    private List<Person> getAuthors(JsonNode contentNode) {
        List<Person> authors = Lists.newArrayList();

        JsonNode authorsNode = contentNode.get(AUTHORS_NODE_NAME);
        if (authorsNode == null)
            return authors;

        for (JsonNode authorNode : authorsNode) {
            Optional<Person> author = getAuthor(authorNode);
            author.ifPresent(authors::add);
        }

        return authors;
    }

    private Optional<Person> getAuthor(JsonNode authorNode) {
        Person.PersonBuilder authorBuilder = Person.builder();

        Optional<String> firstName = getAuthorFirstName(authorNode.textValue());
        if (!firstName.isPresent())
            return Optional.empty();

        authorBuilder.firstName(firstName.get());

        Optional<String> lastName = getAuthorLastName(authorNode.textValue());
        lastName.ifPresent(authorBuilder::lastName);

        return Optional.of(authorBuilder.build());
    }

    private Optional<String> getAuthorFirstName(String authorName) {
        if (isNameEmpty(authorName))
            return Optional.empty();

        String firstName = authorName.trim();
        if (firstName.contains(" "))
            firstName = getFirstPartOfName(firstName);

        return Optional.of(firstName);
    }

    private boolean isNameEmpty(String authorName) {
        return authorName == null || authorName.isEmpty();
    }

    private String getFirstPartOfName(String firstName) {
        return firstName.substring(0, firstName.indexOf(' ')).trim();
    }

    private Optional<String> getAuthorLastName(String authorName) {
        String lastName = authorName.trim();
        if (lastName.contains(" "))
            return Optional.of(getRestOfName(lastName));
        else
            return Optional.empty();
    }

    private String getRestOfName(String lastName) {
        return lastName.substring(lastName.indexOf(' ')).trim();
    }

    private List<Category> getCategories(JsonNode contentNode) {
        List<Category> categories = Lists.newArrayList();

        JsonNode categoriesNode = contentNode.get(CATEGORIES_NODE_NAME);
        if (categoriesNode == null)
            return categories;

        for (JsonNode categoryNode : categoriesNode) {
            categories.add(Category.builder()
                    .name(categoryNode.textValue())
                    .build());
        }

        return categories;
    }

}
