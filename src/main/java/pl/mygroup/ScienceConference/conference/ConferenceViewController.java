package pl.mygroup.ScienceConference.conference;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mygroup.ScienceConference.panel.PanelDTO;
import pl.mygroup.ScienceConference.panel.PanelService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/conference")
@AllArgsConstructor
public class ConferenceViewController {

    private final ConferenceService conferenceService;
    private final PanelService panelService;

    @GetMapping
    public String getConferencesView(Model model){
        List<Conference> conferences = conferenceService.getConferences();
        model.addAttribute("conferences", conferences);
        return "conference";
    }

    @GetMapping("/{id}")
    public String getConference(@PathVariable("id") Long id, Model model){
        Optional<ConferenceDTO> conferenceOptional = conferenceService.getConferenceDTO(id);
        if(conferenceOptional.isEmpty()){
            return "index";
        }
        ConferenceDTO conference = conferenceOptional.get();
        List<PanelDTO> panels = panelService.getPanelsByConference(conference.id());
        model.addAttribute("conference", conference);
        model.addAttribute("panels", panels);
        return "conferenceDetails";
    }

}
