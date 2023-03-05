package pl.mygroup.ScienceConference.conference;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conference")
@AllArgsConstructor
public class ConferenceController {

    private final ConferenceService conferenceService;

    @PostMapping
    public String createConference(@RequestBody Conference conference){
        return conferenceService.createConference(conference);
    }

}
