package pl.mygroup.ScienceConference.panel;

import org.springframework.stereotype.Service;
import pl.mygroup.ScienceConference.article.Article;

import java.util.ArrayList;
import java.util.function.Function;

@Service
public class PanelMapper implements Function<Panel, PanelDTO> {

    @Override
    public PanelDTO apply(Panel panel) {
        String endpoint = "/api/conference/"+panel.getConference().getId();
        PanelDTO panelDTO =  new PanelDTO(panel.getId(),
                            panel.getDescription(),
                            panel.getStartDate(),
                            panel.getEndDate(),
                            panel.getConference().getName(),
                            endpoint,
                            new ArrayList<>());
        for(Article a : panel.getArticles()){
            panelDTO.getArticleIds().add(a.getId());
        }
        return panelDTO;
    }
}
