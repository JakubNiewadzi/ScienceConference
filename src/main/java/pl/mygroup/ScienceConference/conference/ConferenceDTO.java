package pl.mygroup.ScienceConference.conference;

import pl.mygroup.ScienceConference.user.User;

import java.time.LocalDateTime;

public record ConferenceDTO(
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        User organizer
) {
}
