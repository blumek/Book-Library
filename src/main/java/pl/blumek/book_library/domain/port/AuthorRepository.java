package pl.blumek.book_library.domain.port;

import pl.blumek.book_library.domain.entity.Person;

import java.util.List;

public interface AuthorRepository {
    List<Person> findAll();
}
