package pl.mygroup.ScienceConference.conference;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.mygroup.ScienceConference.user.User;
import pl.mygroup.ScienceConference.user.UserRepository;
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
    private final UserRepository userRepository;

    public List<Conference> getConferences() {
        return repository.findAll();
    }

    public Optional<Conference> getConference(Long conferenceId) {
        return repository.findById(conferenceId);
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

        if (repository.findById(id).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Conference conference = repository.findById(id).get();
        Optional<User> organizerOptional = userService.getUser(conferenceDTO.getOrganizerEmail());
        String name = conferenceDTO.getName();
        String description = conferenceDTO.getDescription();
        LocalDateTime startDate = conferenceDTO.getStartDate();
        LocalDateTime endDate = conferenceDTO.getEndDate();


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
        User organizer = organizerOptional.orElseGet(() -> userRepository.findByEmail("admin").get());
        if (!organizer.equals(conference.getOrganizer())
                && organizer.getRole() == UserRole.ADMIN) {
            conference.setOrganizer(organizer);
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> createConference(ConferenceDTO conferenceDTO) {
        if (conferenceDTO.getName() == null ||
                conferenceDTO.getDescription() == null ||
                conferenceDTO.getStartDate() == null ||
                conferenceDTO.getEndDate() == null) {
            return ResponseEntity.badRequest().body("None of conference values can be null");
        }
        if (conferenceDTO.getEndDate().isBefore(conferenceDTO.getStartDate())) {
            return ResponseEntity.badRequest().body("The conference cannot end before it started");
        }


        if (conferenceDTO.getEndDate().isBefore(LocalDateTime.now()) ||
                conferenceDTO.getStartDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Cannot add a conference that has already started or ended");
        }
        User organizer;
        if (conferenceDTO.getOrganizerEmail() == null) {
            organizer = userService.getUser("admin").get();
        }else{
            Optional<User>organizerOptional = userService.getUser(conferenceDTO.getOrganizerEmail());
            organizer=organizerOptional.orElseGet(() -> userService.getUser("admin").get());
        }
        //return ResponseEntity.badRequest().body("Cannot find organizer in users database");
        Conference conference = new Conference();
        conference.setName(conferenceDTO.getName());
        conference.setDescription(conferenceDTO.getDescription());
        conference.setStartDate(conferenceDTO.getStartDate());
        conference.setEndDate(conferenceDTO.getEndDate());
        conference.setOrganizer(organizer);
        repository.save(conference);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> removeConference(Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
