package pl.mygroup.ScienceConference.conference;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceDTO implements Serializable{
        @JsonProperty
        private Long id;
        private String name;
        private String description;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime startDate;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime endDate;
        @JsonProperty
        private String organizerEmail;
}
