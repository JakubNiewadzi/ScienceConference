package pl.mygroup.ScienceConference.conference;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceDTO{
        @JsonProperty
        private Long id;
        private String name;
        private String description;
        @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
        private LocalDateTime startDate;
        @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
        private LocalDateTime endDate;
        @JsonProperty
        private String organizerEmail;
}
