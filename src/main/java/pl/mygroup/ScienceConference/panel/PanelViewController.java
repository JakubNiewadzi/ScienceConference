package pl.mygroup.ScienceConference.panel;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/panel")
@AllArgsConstructor
public class PanelViewController {

    private final PanelService panelService;

    @GetMapping("/{id}")
    public String getPanel(@PathVariable Long id,
                           Model model){
        Optional<PanelDTO> optionalPanelDTO = panelService.getPanel(id);
        if(optionalPanelDTO.isEmpty()){
            return "redirect:/";
        }
        model.addAttribute("panelDTO", optionalPanelDTO.get());
        return "panelDetails";
    }

}
