package pl.blumek.book_library.application.spring_app.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.blumek.book_library.adapter.controller.BookController;
import pl.blumek.book_library.adapter.id_generator.UUIDGenerator;
import pl.blumek.book_library.adapter.repository.InMemoryBookRepository;
import pl.blumek.book_library.domain.port.BookRepository;
import pl.blumek.book_library.usecase.FindBook;


@Configuration
class Config {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        return objectMapper;
    }

    @Bean
    BookController bookController() {
        return new BookController(findBook());
    }

    @Bean
    FindBook findBook() {
        return new FindBook(bookRepository());
    }

    @Bean
    BookRepository bookRepository() {
        return new InMemoryBookRepository(UUIDGenerator.create());
    }
}
