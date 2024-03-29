package pl.mygroup.ScienceConference.conference;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mygroup.ScienceConference.panel.PanelDTO;
import pl.mygroup.ScienceConference.panel.PanelService;
import pl.mygroup.ScienceConference.security.config.WebSecurityConfig;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/conference")
@AllArgsConstructor
public class ConferenceViewController {

    private final ConferenceService conferenceService;
    private final PanelService panelService;
    private final WebSecurityConfig securityConfig;

    @GetMapping
    public String getConferencesView(Model model){
        List<Conference> conferences = conferenceService.getConferences();
        model.addAttribute("isLoggedIn", securityConfig.isLoggedIn());
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
        List<PanelDTO> panels = panelService.getPanelsByConference(conference.getId());
        model.addAttribute("isLoggedIn", securityConfig.isLoggedIn());
        model.addAttribute("conference", conference);
        model.addAttribute("panels", panels);
        return "conferenceDetails";
    }

    @GetMapping("/add")
    public String addConference(Model model){
        if(!SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ADMIN"))){
            model.addAttribute("errorMessage", "Musisz mieć uprawnienia administratora, aby móc dodać nową konferencję!");
            return getConferencesView(model);
        }

        model.addAttribute("conferenceDTO", new ConferenceDTO());
        return "addConference";
    }

    @PostMapping("/add")
    public String createConference(@ModelAttribute ConferenceDTO conferenceDTO){
        conferenceService.createConference(conferenceDTO);
        return "redirect:/conference";
    }

    @GetMapping("/edit/{id}")
    public String editConference(@PathVariable Long id, Model model){
        Optional<ConferenceDTO> optionalConferenceDTO = conferenceService
                .getConferenceDTO(id);
        if(optionalConferenceDTO.isEmpty()){
            return "redirect:/conference";
        }
        model.addAttribute("conferenceDTO", optionalConferenceDTO.get());
        return "editConference";
    }

    @PostMapping("/edit/{id}")
    public String getNewConference(@PathVariable Long id,
                                   @ModelAttribute ConferenceDTO conferenceDTO){
        return updateConfernce(id, conferenceDTO);
    }

    @PutMapping("/edit/{id}")
    public String updateConfernce(Long id, ConferenceDTO conferenceDTO){
        conferenceService.updateConference(id, conferenceDTO);
        return "redirect:/conference";
    }

    @GetMapping("/{conferenceId}/addPanel")
    public String addPanelToConference(@PathVariable Long conferenceId,
                                       Model model){
        PanelDTO panelDTO = new PanelDTO();
        model.addAttribute("panelDTO", panelDTO);
        model.addAttribute("conferenceId", conferenceId);
        return "addPanel";
    }

    @PostMapping("/{conferenceId}/addPanel")
    public String createPanel(@PathVariable Long conferenceId,
                              @ModelAttribute PanelDTO panelDTO){
        panelService.createPanel(conferenceId, panelDTO);
        return "redirect:/conference/{conferenceId}";
    }

    @GetMapping("/delete/{id}")
    public String deleteConference(@PathVariable Long id){
        return removeConference(id);
    }

    @DeleteMapping("/delete/{id}")
    public String removeConference(@PathVariable Long id){
        conferenceService.removeConference(id);
        return "redirect:/conference";
    }
}
