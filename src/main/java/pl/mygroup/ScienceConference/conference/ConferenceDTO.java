package pl.mygroup.ScienceConference.conference;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ConferenceDTO(
        String name,
        String description,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime startDate,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime endDate,
        String organizerEmail
) {
}
