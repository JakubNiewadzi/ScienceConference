package pl.mygroup.ScienceConference.review;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getReviews(){
        List<ReviewDTO> reviews = reviewService.getReviews();
        if(reviews.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long id){
        Optional<ReviewDTO> reviewOptional = reviewService.getReview(id);
        if(reviewOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reviewOptional.get());
    }


}
