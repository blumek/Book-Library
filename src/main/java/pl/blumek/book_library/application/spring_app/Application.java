package pl.blumek.book_library.application.spring_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import picocli.CommandLine;
import pl.blumek.book_library.adapter.id_generator.UUIDGenerator;
import pl.blumek.book_library.adapter.reader.JsonFileAuthorsFromBookGoogleReader;
import pl.blumek.book_library.adapter.reader.JsonFileBookGoogleReader;
import pl.blumek.book_library.adapter.repository.GoogleBooksBookRepository;
import pl.blumek.book_library.adapter.repository.InMemoryAuthorRepository;
import pl.blumek.book_library.adapter.repository.InMemoryBookRepository;
import pl.blumek.book_library.application.spring_app.client.GoogleBooksClient;
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
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@SpringBootApplication
public class Application {
    private static BookRepository bookRepository;
    private static AuthorRepository authorRepository;

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        handleArgs(args);
        SpringApplication.run(Application.class, args);
    }

    private static void handleArgs(String[] args) throws IOException, GeneralSecurityException {
        DataSourceCommand dataSourceCommand = new DataSourceCommand();
        parseArgs(args, dataSourceCommand);

        Optional<File> jsonFile = dataSourceCommand.getJsonFile();
        if (dataSourceCommand.useGoogleBooks()) {
            handleGoogleBooks();
        } else if (jsonFile.isPresent()) {
            handleJsonFile(jsonFile.get());
        } else {
            handleDefault();
        }
    }

    private static void parseArgs(String[] args, DataSourceCommand dataSourceCommand) {
        CommandLine commandLine = new CommandLine(dataSourceCommand);
        commandLine.parseArgs(args);
    }

    private static void handleGoogleBooks() throws GeneralSecurityException, IOException {
        bookRepository = new GoogleBooksBookRepository(GoogleBooksClient.client());

        List<Book> sampleBooks = bookRepository.findAllByCategoryName("Computers");
        authorRepository = new InMemoryAuthorRepository(UUIDGenerator.create(), getAuthorsFromBooks(sampleBooks));
    }

    private static List<Person> getAuthorsFromBooks(List<Book> sampleBooks) {
        return sampleBooks.stream()
                .map(Book::getAuthors)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private static void handleJsonFile(File jsonFile) throws IOException {
        bookRepository = new InMemoryBookRepository(UUIDGenerator.create(), getBooksFromJson(jsonFile));
        authorRepository = new InMemoryAuthorRepository(UUIDGenerator.create(), getAuthorsFromJson(jsonFile));
    }

    private static List<Book> getBooksFromJson(File jsonFile) throws IOException {
        BookReader bookReader = new JsonFileBookGoogleReader(jsonFile);
        return bookReader.read();
    }

    private static List<Person> getAuthorsFromJson(File jsonFile) throws IOException {
        AuthorReader authorReader = new JsonFileAuthorsFromBookGoogleReader(jsonFile);
        return authorReader.read();
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
