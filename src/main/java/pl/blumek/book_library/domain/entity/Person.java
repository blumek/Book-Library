package pl.blumek.book_library.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Value
public class Person {
    String id;
    String firstName;
    String lastName;

    public String getFullName() {
        StringBuilder nameBuilder = new StringBuilder();
        if (isFirstNameAvailable())
            nameBuilder.append(firstName);

        if (isLastNameAvailable())
            nameBuilder.append(" ")
                    .append(lastName);

        return nameBuilder.toString();
    }

    private boolean isFirstNameAvailable() {
        return firstName != null;
    }

    private boolean isLastNameAvailable() {
        return lastName != null;
    }
}
