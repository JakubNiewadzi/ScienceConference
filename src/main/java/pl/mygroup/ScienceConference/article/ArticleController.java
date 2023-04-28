package pl.mygroup.ScienceConference.article;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@AllArgsConstructor
public class ArticleController {

    private ArticleService articleService;

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getArticles(){
        List<ArticleDTO> articles = articleService.getArticles();
        if(articles.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articles);
    }
}
