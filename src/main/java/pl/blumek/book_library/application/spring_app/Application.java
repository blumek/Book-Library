package pl.blumek.book_library.application.spring_app;

import com.google.common.io.Files;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import picocli.CommandLine;
import pl.blumek.book_library.adapter.id_generator.UUIDGenerator;
import pl.blumek.book_library.adapter.reader.JsonFileAuthorsFromBookGoogleReader;
import pl.blumek.book_library.adapter.reader.JsonFileBookGoogleReader;
import pl.blumek.book_library.adapter.repository.InMemoryAuthorRepository;
import pl.blumek.book_library.adapter.repository.InMemoryBookRepository;
import pl.blumek.book_library.application.spring_app.command.DataSourceCommand;
import pl.blumek.book_library.config.Config;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.entity.Person;
import pl.blumek.book_library.domain.port.AuthorReader;
import pl.blumek.book_library.domain.port.AuthorRepository;
import pl.blumek.book_library.domain.port.BookReader;
import pl.blumek.book_library.domain.port.BookRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Application {
    private static final String JSON_EXTENSION = "json";

    private static BookRepository bookRepository;
    private static AuthorRepository authorRepository;

    public static void main(String[] args) throws IOException {
        handleArgs(args);
        SpringApplication.run(Application.class, args);
    }

    private static void handleArgs(String[] args) throws IOException {
        DataSourceCommand dataSourceCommand = new DataSourceCommand();
        CommandLine commandLine = new CommandLine(dataSourceCommand);
        commandLine.parseArgs(args);

        Optional<File> jsonFile = dataSourceCommand.getJsonFile();
        if (jsonFile.isPresent()) {
            handleJsonFile(jsonFile.get());
        } else {
            handleDefault();
        }
    }

    private static void handleJsonFile(File jsonFile) throws IOException {
        if (!Files.getFileExtension(jsonFile.getName()).equals(JSON_EXTENSION))
            throw new IOException("Provided file is not in JSON format");

        BookReader bookReader = new JsonFileBookGoogleReader(jsonFile);
        List<Book> books = bookReader.read();
        bookRepository = new InMemoryBookRepository(UUIDGenerator.create(), books);
        AuthorReader authorReader = new JsonFileAuthorsFromBookGoogleReader(jsonFile);
        List<Person> authors = authorReader.read();
        authorRepository = new InMemoryAuthorRepository(UUIDGenerator.create(), authors);
    }

    private static void handleDefault() {
        bookRepository = new InMemoryBookRepository(UUIDGenerator.create());
        authorRepository = new InMemoryAuthorRepository(UUIDGenerator.create());
    }

    @Bean
    Config config() {
        return Config.builder()
                .bookRepository(bookRepository)
                .authorRepository(authorRepository)
                .build();
    }
}
