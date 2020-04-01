package pl.blumek.book_library.domain.port;

import pl.blumek.book_library.domain.entity.Book;

import java.io.IOException;
import java.util.List;

public interface BookReader {
    List<Book> read() throws IOException;
}
