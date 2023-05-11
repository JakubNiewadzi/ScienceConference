package pl.mygroup.ScienceConference.article;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.mygroup.ScienceConference.panel.Panel;
import pl.mygroup.ScienceConference.panel.PanelRepository;
import pl.mygroup.ScienceConference.review.Review;
import pl.mygroup.ScienceConference.review.ReviewRepository;
import pl.mygroup.ScienceConference.user.User;
import pl.mygroup.ScienceConference.user.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;


    public List<ArticleDTO> getArticles() {
        List<ArticleDTO> articles = articleRepository.findAll().
                stream().map(articleMapper).
                toList();
        articles.forEach(x -> {
            List<Review> reviews = reviewRepository.findByArticleId(x.getId());
            if (reviews.isEmpty()) {
                return;
            }
            double rating = 0.0;
            for (Review r : reviews) {
                rating += r.getRating();
            }
            x.setAverageRating(rating / reviews.size());
        });
        return articles;
    }

    public ArticleDTO getArticle(Long id) {
        Optional<ArticleDTO> articleDTOOptional = articleRepository.findById(id)
                .map(articleMapper);

        if (articleDTOOptional.isEmpty()) {
            return null;
        }
        List<Review> reviews = reviewRepository.findByArticleId(id);
        if (reviews.isEmpty()) {
            return articleDTOOptional.get();
        }

        ArticleDTO article = articleDTOOptional.get();
        double rating = 0.0;
        for (Review r : reviews) {
            rating += r.getRating();
        }
        article.setAverageRating(rating / reviews.size());
        return article;
    }

    public ResponseEntity<String> createArticle(ArticleDTO articleDTO) {
        if (articleDTO.getName() == null ||
                articleDTO.getReference() == null) {
            return ResponseEntity.badRequest().build();
        }

        Article article = new Article();
        article.setName(articleDTO.getName());
        article.setReference(articleDTO.getReference());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = userRepository.findByEmail("admin").get();
        if(!(authentication.getPrincipal() instanceof String)){
            currentUser = (User) authentication.getPrincipal();
        }
        article.setCreator(currentUser);
        articleRepository.save(article);
        return ResponseEntity.ok("Article has been successfully created");
    }

    @Transactional
    public ResponseEntity<ArticleDTO> removeArticle(Long id){
        Optional<Article> removedArticleOptional = articleRepository.findById(id);
        if(removedArticleOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        removedArticleOptional.get().getPanels().clear();
        articleRepository.deleteById(id);
        ArticleDTO removedArticle = removedArticleOptional
                .map(articleMapper).get();
        return ResponseEntity.ok(removedArticle);
    }
//   TODO: artykuły z panelu
//    public Optional<ArticleDTO> getArticlesByPanel(){
//        return ;
//    }
}
