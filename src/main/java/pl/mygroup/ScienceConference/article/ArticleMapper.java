package pl.mygroup.ScienceConference.article;

import org.springframework.stereotype.Service;
import pl.mygroup.ScienceConference.panel.Panel;

import java.util.ArrayList;
import java.util.function.Function;

@Service
public class ArticleMapper implements Function<Article, ArticleDTO> {

    static final double START_RATING = 0.0;
    @Override
    public ArticleDTO apply(Article article) {
        ArticleDTO articleDTO = new ArticleDTO(article.getId(),
                article.getName(),
                article.getReference(),
                START_RATING,
                article.getCreator().getEmail(),
                new ArrayList<>()
        );

        for(Panel p : article.getPanels()){
            articleDTO.getPanelIds().add(p.getId());
        }
        return articleDTO;
    }
}
