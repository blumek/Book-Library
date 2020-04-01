package pl.blumek.book_library.adapter.reader;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.module.SimpleModule;
import pl.blumek.book_library.adapter.deserializer.BookGoogleDeserializer;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.entity.Person;
import pl.blumek.book_library.domain.port.AuthorReader;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class JsonFileAuthorsFromBookGoogleReader implements AuthorReader {
    private static final String BOOKS_ROOT_NAME = "/items";

    private File jsonFile;
    private ObjectReader objectReader;

    public JsonFileAuthorsFromBookGoogleReader(File jsonFile) {
        this.jsonFile = jsonFile;
        configureObjectReader();
    }

    private void configureObjectReader() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Book.class, new BookGoogleDeserializer(Book.class));
        mapper.registerModule(module);
        JavaType bookCollectionType = mapper.getTypeFactory().constructCollectionType(List.class, Book.class);
        this.objectReader = mapper.readerFor(bookCollectionType)
                .at(BOOKS_ROOT_NAME);
    }

    @Override
    public List<Person> read() throws IOException {
        List<Book> books = objectReader.readValue(jsonFile);
        return books.stream()
                .map(Book::getAuthors)
                .flatMap(Collection::stream)
                .distinct()
                .collect(toList());
    }
}
