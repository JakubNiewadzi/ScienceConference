package pl.mygroup.ScienceConference.conference;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mygroup.ScienceConference.user.User;

import java.util.List;

@RestController
@RequestMapping("/api/conference")
@AllArgsConstructor
public class ConferenceController {

    private final ConferenceService conferenceService;

    @PostMapping
    public ResponseEntity<String> createConference(@RequestBody ConferenceDTO conference){
        return conferenceService.createConference(conference);
    }

    @GetMapping
    public ResponseEntity<List<ConferenceDTO>> getConferences(){
        List<ConferenceDTO> conferences = conferenceService.getConferencesDTO();

        return ResponseEntity.ok(conferences);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConferenceDTO> getConference(@PathVariable Long id){
        ConferenceDTO conference = conferenceService.getConferenceDTO(id);

        if(conference!=null){
            return ResponseEntity.ok(conference);
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    public ConferenceDTO updateConference(@PathVariable Long id, @RequestBody ConferenceDTO conferenceDTO){
        return conferenceService.updateConference(id, conferenceDTO);
    }

    @DeleteMapping("/{id}")
    public void removeConference(@PathVariable Long id){
        conferenceService.removeConference(id);
    }
}
