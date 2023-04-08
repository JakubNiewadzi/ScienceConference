package pl.mygroup.ScienceConference.conference;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/conference")
@AllArgsConstructor
public class ConferenceViewController {

    private final ConferenceService conferenceService;

    @GetMapping
    public String getConferencesView(Model model){
        List<Conference> conferences = conferenceService.getConferences();
        model.addAttribute("conferences", conferences);
        return "conference";
    }

    @GetMapping("/{id}")
    public String getConference(@PathVariable("id") Long id, Model model){
        ConferenceDTO conference = conferenceService.getConferenceDTO(id);
        model.addAttribute("conference", conference);
        return "conferenceDetails";
    }

}
