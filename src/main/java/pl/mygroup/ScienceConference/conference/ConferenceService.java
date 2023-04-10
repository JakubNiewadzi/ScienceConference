package pl.mygroup.ScienceConference.conference;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.mygroup.ScienceConference.user.UserDTO;
import pl.mygroup.ScienceConference.user.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConferenceService {

    private final ConferenceRepository repository;
    private final ConferenceMapper conferenceMapper;
    private final UserService userService;

    public List<Conference> getConferences(){
        return repository.findAll();
    }

    public List<ConferenceDTO> getConferencesDTO(){
        return repository.findAll().stream()
                .map(conferenceMapper)
                .collect(Collectors.toList());
    }

    public ConferenceDTO getConferenceDTO(Long id){
        return repository.findById(id)
                .map(conferenceMapper)
                .get();
    }

    @Transactional
    public ConferenceDTO updateConference(Long id,  ConferenceDTO conferenceDTO){
        Conference conference=repository.findById(id).orElseThrow(
                () -> new IllegalStateException("No object with id " + id +" in database")
        );

        String name = conferenceDTO.name();
        String description = conferenceDTO.description();
        LocalDateTime startDate = conferenceDTO.startDate();
        LocalDateTime endDate = conferenceDTO.endDate();
        UserDTO organizer = conferenceDTO.organizer();

        if(name!=null &&
            name.length()>0 &&
            !name.equals(conference.getName())){
            conference.setName(name);
        }

        if(description!=null &&
                description.length()>0 &&
                !description.equals(conference.getDescription())){
            conference.setDescription(description);
        }

        if(startDate!=null){
            if(endDate!=null && startDate.isBefore(endDate)){
                conference.setStartDate(startDate);
                conference.setEndDate(endDate);
            }else if(startDate.isBefore(conference.getEndDate())){
                conference.setStartDate(startDate);
            }else {
                throw new IllegalArgumentException("Start date cannot be after end date");
            }
        } else {
            if(endDate!=null && endDate.isAfter(conference.getStartDate())){
                conference.setEndDate(endDate);
            }
        }

        if (organizer!=null &&
            !organizer.equals(conference.getOrganizer()) &&
            userService.getUser(organizer.email())!=null){
            organizer = userService.getUser(organizer.email());
            conference.setOrganizer(organizer);
        }
        return conferenceMapper.apply(conference);
    }

    public ResponseEntity<String> createConference(ConferenceDTO conferenceDTO){
        if(conferenceDTO.endDate().isBefore(conferenceDTO.startDate())){
            return ResponseEntity.badRequest().body("The conference cannot end before it started");
        }

        if(conferenceDTO.endDate().isBefore(LocalDateTime.now()) ||
                conferenceDTO.startDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Cannot add a conference that has already ended");
        }

        if(userService.getUser(conferenceDTO.organizer().email())==null){
            return ResponseEntity.badRequest().body("Cannot find organizer in users database");
        }

        Conference conference = new Conference();
        conference.setName(conferenceDTO.name());
        conference.setDescription(conferenceDTO.description());
        conference.setStartDate(conferenceDTO.startDate());
        conference.setEndDate(conferenceDTO.endDate());
        conference.setOrganizer(conferenceDTO.organizer());
        repository.save(conference);
        return ResponseEntity.ok().build();
    }

    public void removeConference(Long id){
        repository.deleteById(id);
    }
}
