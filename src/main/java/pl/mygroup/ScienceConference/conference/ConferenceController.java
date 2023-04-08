package pl.mygroup.ScienceConference.conference;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conference")
@AllArgsConstructor
public class ConferenceController {

    private final ConferenceService conferenceService;

    @PostMapping
    public String createConference(@RequestBody Conference conference){
        conferenceService.createConference(conference);
        return "done";
    }

    @GetMapping
    public List<ConferenceDTO> getConferecens(){
        return conferenceService.getConferencesDTO();
    }

    @GetMapping("/{id}")
    public ConferenceDTO getConference(@PathVariable Long id){
        return conferenceService.getConferenceDTO(id);
    }


    @PutMapping("/{id}")
    public String updateConference(@PathVariable Long id, @RequestBody Conference conference){

        return "donezo";
    }

}
