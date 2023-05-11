package pl.mygroup.ScienceConference.panel;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PanelDTO{
        @JsonProperty
        private Long id;
        private String description;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime startDate;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime endDate;
        @JsonProperty
        private String conferenceName;
        @JsonProperty
        private String conferenceEndPoint;
        @JsonProperty
        private List<Long> articleIds;

}
