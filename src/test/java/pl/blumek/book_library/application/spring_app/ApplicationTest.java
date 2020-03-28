package pl.blumek.book_library.application.spring_app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.blumek.book_library.application.spring_app.controller.SpringBookController;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationTest {

    @Autowired
    private SpringBookController bookController;

    @Test
    public void contextLoads() {
        assertNotNull(bookController);
    }
}