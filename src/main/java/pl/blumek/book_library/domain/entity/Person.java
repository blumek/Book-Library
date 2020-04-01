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
        if (firstName != null)
            nameBuilder.append(firstName);

        if (lastName != null)
            nameBuilder.append(" ").append(lastName);

        return nameBuilder.toString();
    }
}
