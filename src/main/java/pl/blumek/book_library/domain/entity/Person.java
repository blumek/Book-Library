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
        return firstName + " " + lastName;
    }
}
