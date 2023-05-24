package pl.mygroup.ScienceConference.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByName(String name);

    @Query("SELECT a FROM Article a JOIN a.panels p WHERE p.id = :panelId")
    List<Article> findArticlesByPanelId(@Param("panelId") Long panelId);

    @Query("SELECT a FROM Article a LEFT JOIN a.panels p WHERE p.id IS NULL OR p.id != :panelId")
    List<Article> findArticlesNotInPanel(@Param("panelId") Long panelId);

}
