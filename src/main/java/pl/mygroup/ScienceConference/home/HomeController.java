package pl.mygroup.ScienceConference.home;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mygroup.ScienceConference.security.config.WebSecurityConfig;

@Controller
@AllArgsConstructor
public class HomeController {

    private final WebSecurityConfig securityConfig;

    @GetMapping
    public String homePage(Model model){
        model.addAttribute("isLoggedIn", securityConfig.isLoggedIn());
        return "index";
    }

}
