package pl.mygroup.ScienceConference.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {

    @JsonProperty
    private Long id;
    private String name;
    private String reference;
    @JsonProperty
    private double averageRating;
    @JsonProperty
    private String creatorName;
    @JsonProperty
    private List<Long> panelIds;

}
