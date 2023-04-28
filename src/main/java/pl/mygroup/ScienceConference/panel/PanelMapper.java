package pl.mygroup.ScienceConference.panel;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PanelMapper implements Function<Panel, PanelDTO> {

    @Override
    public PanelDTO apply(Panel panel) {
        String endpoint = "/api/conference/"+panel.getConference().getId();
        return new PanelDTO(panel.getDescription(),
                            panel.getStartDate(),
                            panel.getEndDate(),
                            panel.getConference().getName(),
                            endpoint);
    }
}
