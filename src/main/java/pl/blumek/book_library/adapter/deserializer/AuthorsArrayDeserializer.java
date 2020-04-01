package pl.blumek.book_library.adapter.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.common.collect.Lists;
import pl.blumek.book_library.domain.entity.Person;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AuthorsArrayDeserializer extends StdDeserializer<List<Person>> {

    public AuthorsArrayDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<Person> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode rootNode =  jsonParser.getCodec().readTree(jsonParser);
        return getDeserializedAuthors(rootNode);
    }

    private List<Person> getDeserializedAuthors(JsonNode authorsNode) {
        List<Person> authors = Lists.newArrayList();
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
}
