package pl.mygroup.ScienceConference.conference;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.mygroup.ScienceConference.user.User;
import pl.mygroup.ScienceConference.user.UserRole;
import pl.mygroup.ScienceConference.user.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConferenceService {

    private final ConferenceRepository repository;
    private final ConferenceMapper conferenceMapper;
    private final UserService userService;

    public List<Conference> getConferences() {
        return repository.findAll();
    }

    public List<ConferenceDTO> getConferencesDTO() {
        return repository.findAll().stream()
                .map(conferenceMapper)
                .collect(Collectors.toList());
    }

    public Optional<ConferenceDTO> getConferenceDTO(Long id) {
        return repository.findById(id)
                .map(conferenceMapper);
    }

    @Transactional
    public ResponseEntity<String> updateConference(Long id, ConferenceDTO conferenceDTO) {

        if(repository.findById(id).isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        Conference conference = repository.findById(id).get();
        User organizer = userService.getUser(conferenceDTO.organizerEmail());
        String name = conferenceDTO.name();
        String description = conferenceDTO.description();
        LocalDateTime startDate = conferenceDTO.startDate();
        LocalDateTime endDate = conferenceDTO.endDate();


        if (name != null &&
                name.length() > 0 &&
                !name.equals(conference.getName())) {
            conference.setName(name);
        }

        if (description != null &&
                description.length() > 0 &&
                !description.equals(conference.getDescription())) {
            conference.setDescription(description);
        }

        if (startDate != null) {
            if (endDate != null && startDate.isBefore(endDate)) {
                conference.setStartDate(startDate);
                conference.setEndDate(endDate);
            } else if (startDate.isBefore(conference.getEndDate())) {
                conference.setStartDate(startDate);
            } else {
                return ResponseEntity.badRequest().body("Start date cannot be after end date");
            }
        } else {
            if (endDate != null && endDate.isAfter(conference.getStartDate())) {
                conference.setEndDate(endDate);
            }
        }

        if (!organizer.equals(conference.getOrganizer())
                && organizer.getRole() == UserRole.ADMIN) {
            conference.setOrganizer(organizer);
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> createConference(ConferenceDTO conferenceDTO) {
        if (conferenceDTO.name() == null ||
                conferenceDTO.description() == null ||
                conferenceDTO.startDate() == null ||
                conferenceDTO.endDate() == null ||
                conferenceDTO.organizerEmail() == null) {
            return ResponseEntity.badRequest().body("None of conference values can be null");
        }
        if (conferenceDTO.endDate().isBefore(conferenceDTO.startDate())) {
            return ResponseEntity.badRequest().body("The conference cannot end before it started");
        }


        if (conferenceDTO.endDate().isBefore(LocalDateTime.now()) ||
                conferenceDTO.startDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Cannot add a conference that has already started or ended");
        }

        if (userService.getUserDTO(conferenceDTO.organizerEmail()) == null) {
            return ResponseEntity.badRequest().body("Cannot find organizer in users database");
        }

        User organizer = userService.getUser(conferenceDTO.organizerEmail());

        Conference conference = new Conference();
        conference.setName(conferenceDTO.name());
        conference.setDescription(conferenceDTO.description());
        conference.setStartDate(conferenceDTO.startDate());
        conference.setEndDate(conferenceDTO.endDate());
        conference.setOrganizer(organizer);
        repository.save(conference);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> removeConference(Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
