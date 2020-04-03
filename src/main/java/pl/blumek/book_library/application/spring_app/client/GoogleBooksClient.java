package pl.blumek.book_library.application.spring_app.client;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.security.GeneralSecurityException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GoogleBooksClient {
    private static final String APP_NAME = "book-library";
    private static final String API_KEY = "AIzaSyBMVGrhr6qFpCP504pUba_xXYFrtuoy3DA";

    public static Books client() throws GeneralSecurityException, IOException {
        return new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
                .setApplicationName(APP_NAME)
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY))
                .build();
    }
}
