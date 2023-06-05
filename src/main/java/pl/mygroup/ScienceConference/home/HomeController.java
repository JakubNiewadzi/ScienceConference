package pl.mygroup.ScienceConference.home;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mygroup.ScienceConference.article.ArticleDTO;
import pl.mygroup.ScienceConference.article.ArticleService;
import pl.mygroup.ScienceConference.conference.ConferenceDTO;
import pl.mygroup.ScienceConference.conference.ConferenceService;
import pl.mygroup.ScienceConference.security.config.WebSecurityConfig;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class HomeController {

    private final WebSecurityConfig securityConfig;
    private final ConferenceService conferenceService;
    private final ArticleService articleService;

    @GetMapping
    public String homePage(Model model){
        model.addAttribute("isLoggedIn", securityConfig.isLoggedIn());
        List<ConferenceDTO> ongoingConferences = conferenceService.getOngoingConferences();
        System.out.println(ongoingConferences);
        model.addAttribute("ongoingConferences", ongoingConferences);
        List<ConferenceDTO> nearConferences = conferenceService.getNearConferences();
        System.out.println(nearConferences);
        model.addAttribute("nearConferences", nearConferences);
        Optional<ArticleDTO> highestRatedArticle = articleService.getHighestRatedArticle();
        if(highestRatedArticle.isPresent()){
            model.addAttribute("highestRatedArticle", highestRatedArticle);
        }
        return "index";
    }

}
