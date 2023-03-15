package pl.mygroup.ScienceConference.panel;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PanelService {

    private final PanelRepository repository;

    public String createPanel(Panel panel){
        repository.save(panel);
        return "panel success";
    }

}
