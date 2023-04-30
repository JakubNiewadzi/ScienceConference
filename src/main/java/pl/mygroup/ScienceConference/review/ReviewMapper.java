package pl.mygroup.ScienceConference.review;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ReviewMapper implements Function<Review, ReviewDTO> {

    private final String ARTICLES_ENDPOINT = "/api/article/";

    @Override
    public ReviewDTO apply(Review review) {
        return new ReviewDTO(review.getId(),
                review.getReviewContent(),
                review.getRating(),
                review.getArticle().getName(),
                ARTICLES_ENDPOINT + review.getArticle().getId(),
                review.getReviewer().getEmail());
    }
}
