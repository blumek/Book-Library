package pl.blumek.book_library.adapter.repository;

import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume;
import com.google.common.collect.Lists;
import pl.blumek.book_library.adapter.repository.converter.GoogleBookConverter;
import pl.blumek.book_library.domain.entity.Book;
import pl.blumek.book_library.domain.port.BookRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GoogleBooksBookRepository implements BookRepository {
    private static final String FIELDS = "items/id,items/volumeInfo";
    private final Books books;

    public GoogleBooksBookRepository(Books books) {
        this.books = books;
    }

    @Override
    public Optional<Book> findById(String id) {
        try {
            Optional<Volume> googleBook = getById(id);
            return googleBook.map(GoogleBookConverter::toBook);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private Optional<Volume> getById(String id) throws IOException {
        Books.Volumes.List list = books.volumes()
                .list(idQuery(id));

        List<Volume> requestedBooks = list.execute()
                .getItems();
        if (requestedBooks == null)
            return Optional.empty();

        return requestedBooks.stream()
                .filter(volume -> id.equalsIgnoreCase(volume.getId()))
                .findFirst();
    }

    private String idQuery(String id) {
        return "id:" + id;
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        try {
            Optional<Volume> googleBook = getByIsbn(isbn);
            return googleBook.map(GoogleBookConverter::toBook);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private Optional<Volume> getByIsbn(String isbn) throws IOException {
        Books.Volumes.List list = books.volumes()
                .list(isbnQuery(isbn))
                .setFields(FIELDS);

        List<Volume> requestedBooks = list.execute()
                .getItems();
        if (requestedBooks == null)
            return Optional.empty();

        return requestedBooks.stream()
                .findFirst();
    }

    private String isbnQuery(String isbn) {
        return "isbn:" + isbn;
    }

    @Override
    public List<Book> findAllByCategoryName(String categoryName) {
        try {
            List<Volume> googleBooks = getAllByCategoryName(categoryName);
            return getConvertedBooks(googleBooks);
        } catch (IOException e) {
            return Lists.newArrayList();
        }
    }

    private List<Book> getConvertedBooks(List<Volume> googleBooks) {
        return googleBooks.stream()
                .map(GoogleBookConverter::toBook)
                .collect(Collectors.toList());
    }

    private List<Volume> getAllByCategoryName(String categoryName) throws IOException {
        Books.Volumes.List list = books.volumes()
                .list(categoryQuery(categoryName))
                .setFields(FIELDS);

        List<Volume> requestedBooks = list.execute().getItems();
        if (requestedBooks == null)
            return Lists.newArrayList();

        return requestedBooks;
    }

    private String categoryQuery(String categoryName) {
        return "subject:" + categoryName;
    }

    @Override
    public List<Book> findAllByAuthorName(String authorName) {
        try {
            List<Volume> googleBooks = getAllByAuthorName(authorName);
            return getConvertedBooks(googleBooks);
        } catch (IOException e) {
            return Lists.newArrayList();
        }
    }

    private List<Volume> getAllByAuthorName(String authorName) throws IOException {
        Books.Volumes.List list = books.volumes()
                .list(authorQuery(authorName))
                .setFields(FIELDS);

        List<Volume> requestedBooks = list.execute().getItems();
        if (requestedBooks == null)
            return Lists.newArrayList();

        return requestedBooks;
    }

    private String authorQuery(String authorName) {
        return "inauthor:" + authorName;
    }
}
