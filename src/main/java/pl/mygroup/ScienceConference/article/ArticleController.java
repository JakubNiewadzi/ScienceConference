package pl.mygroup.ScienceConference.article;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mygroup.ScienceConference.review.ReviewDTO;
import pl.mygroup.ScienceConference.review.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@AllArgsConstructor
public class ArticleController {

    private ArticleService articleService;
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getArticles(){
        List<ArticleDTO> articles = articleService.getArticles();
        if(articles.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticle(@PathVariable Long id){
        ArticleDTO article = articleService.getArticle(id);
        if(article==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
    }

    @PostMapping
    public ResponseEntity<String> createArticle(@RequestBody ArticleDTO articleDTO){
        return articleService.createArticle(articleDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ArticleDTO> removeArticle(@PathVariable Long id){
        return articleService.removeArticle(id);
    }

    @PostMapping("/{articleId}")
    public ResponseEntity<String> createReview(@RequestBody ReviewDTO reviewDTO,
                                               @PathVariable Long articleId){
        return reviewService.createReview(reviewDTO, articleId);
    }
}
