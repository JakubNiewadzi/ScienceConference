package pl.mygroup.ScienceConference.panel;

import pl.mygroup.ScienceConference.conference.ConferenceDTO;

import java.time.LocalDateTime;

public record PanelDTO(
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        ConferenceDTO conference
) {
}
