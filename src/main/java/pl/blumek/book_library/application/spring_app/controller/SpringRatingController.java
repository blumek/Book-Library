package pl.blumek.book_library.application.spring_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.blumek.book_library.adapter.controller.RatingController;
import pl.blumek.book_library.adapter.controller.model.AuthorRatingWeb;

import java.util.List;

@RestController
public class SpringRatingController {
    private final RatingController ratingController;

    @Autowired
    public SpringRatingController(RatingController ratingController) {
        this.ratingController = ratingController;
    }

    @GetMapping("/ratings/authors")
    public List<AuthorRatingWeb> findAllAuthorsRatings() {
        return ratingController.findAllAuthorsRatings();
    }

}
