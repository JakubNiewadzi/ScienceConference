package pl.mygroup.ScienceConference.conference;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConferenceService {

    private final ConferenceRepository repository;

    public String createConference(Conference conference){
        repository.save(conference);
        return "success";
    }
}
