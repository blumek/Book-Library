package pl.blumek.book_library.application.spring_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.blumek.book_library.adapter.controller.BookController;
import pl.blumek.book_library.adapter.controller.model.BookWeb;

@RestController
public class SpringBookController {
    private final BookController bookController;

    @Autowired
    public SpringBookController(BookController bookController) {
        this.bookController = bookController;
    }

    @GetMapping("/books/{isbn}")
    public BookWeb findByIsbn(@PathVariable String isbn) {
        return bookController.findByIsbn(isbn);
    }
}
