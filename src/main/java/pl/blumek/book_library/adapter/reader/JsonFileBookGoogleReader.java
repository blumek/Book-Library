package pl.blumek.book_library.adapter.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.module.SimpleModule;
import pl.blumek.book_library.adapter.deserializer.BookGoogleDeserializer;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.port.BookReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonFileBookGoogleReader implements BookReader {
    private static final String BOOKS_ROOT_NAME = "/items";

    private File jsonFile;
    private ObjectReader objectReader;

    public JsonFileBookGoogleReader(File jsonFile) {
        this.jsonFile = jsonFile;
        configureObjectReader();
    }

    private void configureObjectReader() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Book.class, new BookGoogleDeserializer(Book.class));
        mapper.registerModule(module);
        this.objectReader = mapper.readerFor(new TypeReference<List<Book>>() {})
                .at(BOOKS_ROOT_NAME);
    }

    @Override
    public List<Book> read() throws IOException {
        return objectReader.readValue(jsonFile);
    }
}
