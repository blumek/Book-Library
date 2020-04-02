package pl.blumek.book_library.adapter.repository.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.blumek.book_library.domain.entity.Person;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorNameConverter {

    public static Person toAuthor(String authorName) {
        Person.PersonBuilder authorBuilder = Person.builder();
        authorName = authorName.trim();

        Optional<String> firstName = getAuthorFirstName(authorName);
        if (!firstName.isPresent())
            return authorBuilder.build();

        authorBuilder.firstName(firstName.get());

        Optional<String> lastName = getAuthorLastName(nameWithout(authorName, firstName.get()).trim());
        lastName.ifPresent(authorBuilder::lastName);

        return authorBuilder.build();
    }

    private static String nameWithout(String authorName, String firstName) {
        return authorName.replace(firstName, "");
    }

    private static Optional<String> getAuthorFirstName(String authorName) {
        if (isEmpty(authorName))
            return Optional.empty();

        if (authorName.contains(" "))
            return Optional.of(getFirstPartOfName(authorName));

        return Optional.of(authorName);
    }

    private static boolean isEmpty(String authorName) {
        return authorName == null || authorName.isEmpty();
    }

    private static String getFirstPartOfName(String firstName) {
        return firstName.substring(0, firstName.indexOf(' ')).trim();
    }

    private static Optional<String> getAuthorLastName(String restOfName) {
        if (restOfName.isEmpty())
            return Optional.empty();

        return Optional.of(restOfName.trim());
    }
}
