package pl.mygroup.ScienceConference.article;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mygroup.ScienceConference.panel.Panel;
import pl.mygroup.ScienceConference.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "article_panel",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "panel_id")
    )
    private List<Panel> panels = new ArrayList<>();
}
