package pl.blumek.book_library.application.spring_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.blumek.book_library.application.spring_app.command.DataSourceCommandExecutor;
import pl.blumek.book_library.config.Config;
import pl.blumek.book_library.domain.port.AuthorRepository;
import pl.blumek.book_library.domain.port.BookRepository;

import java.io.IOException;
import java.security.GeneralSecurityException;


@SpringBootApplication
public class Application {
    private static BookRepository bookRepository;
    private static AuthorRepository authorRepository;

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        DataSourceCommandExecutor dataSourceCommandExecutor = DataSourceCommandExecutor.execute(args);

        bookRepository = dataSourceCommandExecutor.getBookRepository();
        authorRepository = dataSourceCommandExecutor.getAuthorRepository();

        SpringApplication.run(Application.class, args);
    }



    @Bean
    Config config() {
        return Config.builder()
                .bookRepository(bookRepository)
                .authorRepository(authorRepository)
                .build();
    }
}
