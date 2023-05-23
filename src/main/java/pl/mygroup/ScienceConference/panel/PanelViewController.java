package pl.mygroup.ScienceConference.panel;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mygroup.ScienceConference.article.ArticleDTO;
import pl.mygroup.ScienceConference.article.ArticleService;
import pl.mygroup.ScienceConference.security.config.WebSecurityConfig;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/panel")
@AllArgsConstructor
public class PanelViewController {

    private final PanelService panelService;
    private final ArticleService articleService;
    private final WebSecurityConfig securityConfig;

    @GetMapping("/{id}")
    public String getPanel(@PathVariable Long id,
                           Model model){
        Optional<PanelDTO> optionalPanelDTO = panelService.getPanel(id);
        if(optionalPanelDTO.isEmpty()){
            return "redirect:/";
        }
        List<ArticleDTO> articles = articleService.getArticlesByPanel(id);
        System.out.println(articles);
        model.addAttribute("isLoggedIn", securityConfig.isLoggedIn());
        model.addAttribute("panel", optionalPanelDTO.get());
        model.addAttribute("articles", articles);
        return "panelDetails";
    }

}
