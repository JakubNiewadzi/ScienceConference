package pl.mygroup.ScienceConference.panel;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mygroup.ScienceConference.article.Article;
import pl.mygroup.ScienceConference.conference.Conference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Panel {

    @SequenceGenerator(name = "panel_sequence",
                        sequenceName = "panel_sequence",
                        allocationSize = 1)
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "panel_sequence"
    )
    private Long id;
    @Column(length = 2000)
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @ManyToMany(mappedBy = "panels",
            cascade = CascadeType.ALL)
    private List<Article> articles = new ArrayList<>();
}
