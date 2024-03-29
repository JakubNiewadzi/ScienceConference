package pl.mygroup.ScienceConference.review;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.mygroup.ScienceConference.article.Article;
import pl.mygroup.ScienceConference.article.ArticleRepository;
import pl.mygroup.ScienceConference.user.User;
import pl.mygroup.ScienceConference.user.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public List<ReviewDTO> getReviews(){
        return reviewRepository.findAll()
                .stream().map(reviewMapper).toList();
    }

    public Optional<ReviewDTO> getReview(Long id){
        return reviewRepository.findById(id)
                .map(reviewMapper);
    }

    public ResponseEntity<String> createReview(ReviewDTO reviewDTO, Long articleId){
        if(reviewDTO.getReviewContent() == null){
            return ResponseEntity.badRequest().body("Review content cannot be null");
        }

        if(reviewDTO.getRating() < 0.00 ||
                reviewDTO.getRating() > 5.00){
            return ResponseEntity.badRequest().body("Review rating has to be between 0 and 5");
        }
        if(reviewDTO.getReviewContent().isBlank() ||
           reviewDTO.getReviewContent().isEmpty()){
            return ResponseEntity.badRequest().body("Review content cannot be empty or blank");
        }
        Optional<Article> articleOptional = articleRepository.findById(articleId);

        if(articleOptional.isEmpty()){
            return ResponseEntity.badRequest().body("Article you want to review does not exit");
        }

        Review review = new Review();
        review.setReviewContent(reviewDTO.getReviewContent());
        review.setRating(reviewDTO.getRating());
        review.setArticle(articleOptional.get());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = userRepository.findByEmail("admin").get();
        if(!(authentication.getPrincipal() instanceof String)){
            currentUser = (User) authentication.getPrincipal();
        }
        final User reviewer = currentUser;

        if(currentUser.equals(articleOptional.get().getCreator())){
            return ResponseEntity.badRequest().body("You cannot review your own article!");
        }

        List<Review> reviews = reviewRepository.findAll();

        if(reviews.stream().anyMatch(r -> r.getReviewer()
                .equals(reviewer))){
            return ResponseEntity.badRequest().body("This reviewer has already made a review of this article!");
        }

        review.setReviewer(currentUser);
        reviewRepository.save(review);
        return ResponseEntity.ok().body("Review successfully created");
    }

    public ResponseEntity<ReviewDTO> removeReview(Long id){
        Optional<ReviewDTO> removedReviewOptional = reviewRepository
                .findById(id).map(reviewMapper);

        if(removedReviewOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        ReviewDTO removedReview = removedReviewOptional.get();
        reviewRepository.deleteById(id);
        return ResponseEntity.ok(removedReview);
    }

    public List<ReviewDTO> getReviewsByArticle(Long articleId){
        return reviewRepository.findByArticleId(articleId)
                .stream().map(reviewMapper).toList();
    }

    @Transactional
    public ResponseEntity<String> updateReview(Long reviewId, ReviewDTO reviewDTO){
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);

        if(reviewOptional.isEmpty()){
            return ResponseEntity.badRequest().body("There is no such review");
        }
        Review review = reviewOptional.get();
        if(reviewDTO.getReviewContent().isEmpty() ||
                reviewDTO.getReviewContent().isBlank()){
            return ResponseEntity.badRequest().body("Review content cannot be blank or empty");
        }
        if(reviewDTO.getRating() < 0.00 || reviewDTO.getRating() > 5.00){
            return ResponseEntity.badRequest().body("Rating has to be between 0.00 and 5.00");
        }
        review.setReviewContent(reviewDTO.getReviewContent());
        review.setRating(reviewDTO.getRating());
        return ResponseEntity.ok().body("Review successfully updated");
    }
}
