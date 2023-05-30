package pl.mygroup.ScienceConference.panel;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        model.addAttribute("isLoggedIn", securityConfig.isLoggedIn());
        model.addAttribute("panel", optionalPanelDTO.get());
        model.addAttribute("articles", articles);
        return "panelDetails";
    }

    @GetMapping("/edit/{id}")
    public String editPanel(@PathVariable Long id,
                            Model model){
        Optional<PanelDTO> optionalPanelDTO = panelService.getPanel(id);
        if(optionalPanelDTO.isEmpty()){
            return "redirect:/conference";
        }
        model.addAttribute("panel", optionalPanelDTO.get());
        return "editPanel";
    }

    @PostMapping("/edit/{id}")
    public String getNewPanel(@PathVariable Long id,
                              @ModelAttribute PanelDTO panelDTO){
        return updatePanel(id, panelDTO);
    }

    @PutMapping("/edit/{id}")
    public String updatePanel(@PathVariable Long id, PanelDTO panelDTO){
        panelService.updatePanel(id, panelDTO);
        return "redirect:/panel/{id}";
    }

    @GetMapping("/addArticle/{id}")
    public String getArticlesNotInPanel(@PathVariable Long id, Model model){
        List<ArticleDTO> artclesNotInPanel = articleService.getArticlesNotInPanel(id);
        System.out.println(artclesNotInPanel);
        if(!artclesNotInPanel.isEmpty()) {
            model.addAttribute("articles", artclesNotInPanel);
            model.addAttribute("id", id);
            return "addArticleToPanel";
        }
        model.addAttribute("noArticles", true);
        return "redirect:/panel/{id}";
    }

    @GetMapping("/addArticle/{panelId}/{articleId}")
    public String getArticleToAdd(@PathVariable Long panelId,
                                  @PathVariable Long articleId){
        return addArticleToPanel(panelId, articleId);
    }

    @PatchMapping("/addArticle/{panelId}/{articleId}")
    public String addArticleToPanel(@PathVariable Long panelId,
                                  @PathVariable Long articleId){
        panelService.addArticleToPanel(panelId, articleId);
        return "redirect:/panel/{panelId}";
    }

}
