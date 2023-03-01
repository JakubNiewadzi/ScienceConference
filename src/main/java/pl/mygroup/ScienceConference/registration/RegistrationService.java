package pl.mygroup.ScienceConference.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mygroup.ScienceConference.user.User;
import pl.mygroup.ScienceConference.user.UserRole;
import pl.mygroup.ScienceConference.user.UserService;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;

    public String register(RegistrationRequest request){
        return userService.signUpUser(new User(
            request.getFirstName(),
                request.getFirstName(),
                request.getEmail(),
                request.getPassword(),
                UserRole.SCIENTIST
        ));
    }
}
