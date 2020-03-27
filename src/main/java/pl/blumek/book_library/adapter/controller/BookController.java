package pl.blumek.book_library.adapter.controller;

import pl.blumek.book_library.adapter.controller.model.BookWeb;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.exception.BookNotFoundException;
import pl.blumek.book_library.usecase.FindBook;

import java.util.Optional;

public class BookController {
    private FindBook findBook;

    public BookController(FindBook findBook) {
        this.findBook = findBook;
    }

    public BookWeb findByIsbn(String isbn) {
        Optional<Book> book = findBook.findByIsbn(isbn);
        return BookWeb.from(book.orElseThrow(() -> new BookNotFoundException(isbn)));
    }
}
