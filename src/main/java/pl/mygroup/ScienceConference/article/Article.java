package pl.mygroup.ScienceConference.article;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mygroup.ScienceConference.user.User;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Article {

    @SequenceGenerator(name = "article_sequence",
            sequenceName = "article_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "article_sequence"
    )
    private Long Id;
    private String name;
    private String reference;
    @JoinColumn(name="creator_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;
}
