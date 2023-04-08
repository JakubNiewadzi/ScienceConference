package pl.mygroup.ScienceConference.conference;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConferenceService {

    private final ConferenceRepository repository;
    private final ConferenceMapper conferenceMapper;

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

    public String createConference(Conference conference){
        repository.save(conference);
        return "success";
    }
}
