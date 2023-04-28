package pl.mygroup.ScienceConference.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {

    private Long id;
    private String name;
    private String reference;
    private double averageRating;
    private String creatorName;

}
