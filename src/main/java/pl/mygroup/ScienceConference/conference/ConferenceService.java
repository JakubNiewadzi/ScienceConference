package pl.mygroup.ScienceConference.conference;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConferenceService {

    private final ConferenceRepository repository;

    public List<Conference> getConferences(){
        return repository.findAll();
    }

    public String createConference(Conference conference){
        repository.save(conference);
        return "success";
    }
}
