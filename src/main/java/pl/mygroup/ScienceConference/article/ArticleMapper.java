package pl.mygroup.ScienceConference.article;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ArticleMapper implements Function<Article, ArticleDTO> {


    @Override
    public ArticleDTO apply(Article article) {
        return new ArticleDTO(article.getId(),
                article.getName(),
                article.getReference(),
                0.0,
                article.getCreator().getEmail()
        );
    }
}
