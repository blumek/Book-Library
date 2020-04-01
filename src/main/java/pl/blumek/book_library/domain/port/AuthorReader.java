package pl.blumek.book_library.domain.port;

import pl.blumek.book_library.domain.entity.Person;

import java.io.IOException;
import java.util.List;

public interface AuthorReader {
    public List<Person> read() throws IOException;
}
