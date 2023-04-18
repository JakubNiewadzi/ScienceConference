package pl.mygroup.ScienceConference.conference;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mygroup.ScienceConference.user.User;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/conference")
@AllArgsConstructor
public class ConferenceController {

    private final ConferenceService conferenceService;

    @PostMapping
    public ResponseEntity<String> createConference(@RequestBody ConferenceDTO conference) {
        return conferenceService.createConference(conference);
    }

    @GetMapping
    public ResponseEntity<List<ConferenceDTO>> getConferences() {
        List<ConferenceDTO> conferences = conferenceService.getConferencesDTO();
        if (!conferences.isEmpty()) {
            return ResponseEntity.ok(conferences);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConferenceDTO> getConference(@PathVariable Long id) {
        Optional<ConferenceDTO> conference = conferenceService.getConferenceDTO(id);

        return conference.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateConference(@PathVariable Long id, @RequestBody ConferenceDTO conferenceDTO) {
        return conferenceService.updateConference(id, conferenceDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeConference(@PathVariable Long id) {
        return conferenceService.removeConference(id);
    }
}
