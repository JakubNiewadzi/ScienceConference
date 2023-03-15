package pl.mygroup.ScienceConference.panel;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mygroup.ScienceConference.conference.Conference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Panel {

    @Id
    private Long id;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "conference_id")
    private Conference conference;

}
