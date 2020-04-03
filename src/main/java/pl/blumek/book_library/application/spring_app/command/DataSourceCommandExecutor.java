package pl.blumek.book_library.application.spring_app.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import picocli.CommandLine;
import pl.blumek.book_library.adapter.id_generator.UUIDGenerator;
import pl.blumek.book_library.adapter.reader.JsonFileAuthorsFromBookGoogleReader;
import pl.blumek.book_library.adapter.reader.JsonFileBookGoogleReader;
import pl.blumek.book_library.adapter.repository.GoogleBooksBookRepository;
import pl.blumek.book_library.adapter.repository.InMemoryAuthorRepository;
import pl.blumek.book_library.adapter.repository.InMemoryBookRepository;
import pl.blumek.book_library.application.spring_app.client.GoogleBooksClient;
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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DataSourceCommandExecutor {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public static DataSourceCommandExecutor execute(String[] args) throws IOException, GeneralSecurityException {
        DataSourceCommandExecutor dataSourceCommandExecutor = new DataSourceCommandExecutor();
        dataSourceCommandExecutor.handleArgs(args);
        return dataSourceCommandExecutor;
    }

    public void handleArgs(String[] args) throws IOException, GeneralSecurityException {
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

    private void parseArgs(String[] args, DataSourceCommand dataSourceCommand) {
        CommandLine commandLine = new CommandLine(dataSourceCommand);
        commandLine.parseArgs(args);
    }

    private void handleGoogleBooks() throws GeneralSecurityException, IOException {
        bookRepository = new GoogleBooksBookRepository(GoogleBooksClient.client());

        List<Book> sampleBooks = bookRepository.findAllByCategoryName("Computers");
        authorRepository = new InMemoryAuthorRepository(UUIDGenerator.create(), getAuthorsFromBooks(sampleBooks));
    }

    private List<Person> getAuthorsFromBooks(List<Book> sampleBooks) {
        return sampleBooks.stream()
                .map(Book::getAuthors)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private void handleJsonFile(File jsonFile) throws IOException {
        bookRepository = new InMemoryBookRepository(UUIDGenerator.create(), getBooksFromJson(jsonFile));
        authorRepository = new InMemoryAuthorRepository(UUIDGenerator.create(), getAuthorsFromJson(jsonFile));
    }

    private List<Book> getBooksFromJson(File jsonFile) throws IOException {
        BookReader bookReader = new JsonFileBookGoogleReader(jsonFile);
        return bookReader.read();
    }

    private List<Person> getAuthorsFromJson(File jsonFile) throws IOException {
        AuthorReader authorReader = new JsonFileAuthorsFromBookGoogleReader(jsonFile);
        return authorReader.read();
    }

    private void handleDefault() {
        bookRepository = new InMemoryBookRepository(UUIDGenerator.create());
        authorRepository = new InMemoryAuthorRepository(UUIDGenerator.create());
    }
}
