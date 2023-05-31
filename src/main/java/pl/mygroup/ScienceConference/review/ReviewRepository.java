package pl.mygroup.ScienceConference.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.article.id=:articleId")
    List<Review> findByArticleId(@Param("articleId") Long articleId);

    @Modifying
    @Query("Delete FROM Review r WHERE r.article.id=:articleId")
    void removeAllReviewsFromArticle(@Param("articleId") Long articleId);
}
