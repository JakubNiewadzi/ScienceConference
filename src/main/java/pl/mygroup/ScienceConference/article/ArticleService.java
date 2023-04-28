package pl.mygroup.ScienceConference.article;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.mygroup.ScienceConference.review.Review;
import pl.mygroup.ScienceConference.review.ReviewRepository;

import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class ArticleService {

    private ArticleRepository articleRepository;
    private ArticleMapper articleMapper;
    private ReviewRepository reviewRepository;

    public List<ArticleDTO> getArticles(){
        List<ArticleDTO> articles = articleRepository.findAll().
                stream().map(articleMapper).
                toList();
        articles.stream().forEach(x->{
            List<Review> reviews = reviewRepository.findByArticleId(x.getId());
            if(reviews.isEmpty()){
                return;
            }
            double rating = 0.0;
            for (Review r : reviews){
                rating=rating+r.getRating();
            }
            x.setAverageRating(rating/reviews.size());
        });
        return articles;
    }

}
