package pl.blumek.book_library.domain.exception;

public class BookNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Book not found";

    public BookNotFoundException() {
    }

    public BookNotFoundException(String isbn) {
        super(String.format(DEFAULT_MESSAGE + ", isbn: %s", isbn));
    }
}
