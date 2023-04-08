package pl.mygroup.ScienceConference.conference;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ConferenceMapper implements Function<Conference, ConferenceDTO> {


    @Override
    public ConferenceDTO apply(Conference conference) {
        return new ConferenceDTO(
                conference.getName(),
                conference.getDescription(),
                conference.getStartDate(),
                conference.getEndDate(),
                conference.getOrganizer()
        );
    }
}
