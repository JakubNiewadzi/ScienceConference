package pl.mygroup.ScienceConference.conference;


import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mygroup.ScienceConference.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Conference {

    @SequenceGenerator(name = "conference_sequence",
            sequenceName = "conference_sequence",
            allocationSize = 1
            )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "conference_sequence"
    )
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="organizer_id")
    private User organizer;
}
