package pl.mygroup.ScienceConference.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    @JsonProperty
    private Long id;
    private String reviewContent;
    private double rating;
    @JsonProperty
    private String articleName;
    @JsonProperty
    private String articleEndpoint;
    @JsonProperty
    private String reviewerEmail;
}
