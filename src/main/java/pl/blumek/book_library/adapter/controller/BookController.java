package pl.blumek.book_library.adapter.controller;

import pl.blumek.book_library.adapter.controller.model.BookWeb;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.exception.BookNotFoundException;
import pl.blumek.book_library.usecase.FindBook;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class BookController {
    private final FindBook findBook;

    public BookController(FindBook findBook) {
        this.findBook = findBook;
    }

    public BookWeb findByIsbn(String isbn) {
        Optional<Book> book = findBook.findByIsbn(isbn);
        return BookWeb.from(book.orElseThrow(() -> new BookNotFoundException(isbn)));
    }

    public List<BookWeb> findAllByCategoryName(String categoryName) {
        return findBook.findAllByCategoryName(categoryName).stream()
                .map(BookWeb::from)
                .collect(toList());
    }
}
