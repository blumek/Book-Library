package pl.blumek.book_library.application.spring_app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.blumek.book_library.adapter.controller.BookController;
import pl.blumek.book_library.adapter.controller.RatingController;
import pl.blumek.book_library.config.Config;


@Configuration
class SpringConfig {
    private final Config config;

    @Autowired
    public SpringConfig(Config config) {
        this.config = config;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return config.objectMapper();
    }

    @Bean
    BookController bookController() {
        return config.bookController();
    }

    @Bean
    RatingController ratingController() {
        return config.ratingController();
    }

}
