package pl.mygroup.ScienceConference.review;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mygroup.ScienceConference.article.Article;
import pl.mygroup.ScienceConference.user.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class Review {

    @SequenceGenerator(name = "review_sequence",
            sequenceName = "review_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "review_sequence"
    )
    private Long id;
    private String reviewContent;
    private double rating;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;
}
