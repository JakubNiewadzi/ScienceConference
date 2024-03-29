package pl.mygroup.ScienceConference.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationViewController {

    private final RegistrationService registrationService;

    @GetMapping
    public String getRegistration(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser( @ModelAttribute RegistrationRequest request) {

        registrationService.register(request);
        return "redirect:/";
    }

}