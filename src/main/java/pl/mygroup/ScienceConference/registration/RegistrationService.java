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
        if(request.getEmail().isEmpty() ||
                request.getFirstName().isEmpty() ||
                request.getLastName().isEmpty() ||
                request.getPassword().isEmpty()||
                request.getRepeatPassword().isEmpty()
        ){
            throw new IllegalArgumentException("None of the fields can be empty");
        }
        if(!request.getPassword().equals(request.getRepeatPassword())){
            throw new IllegalArgumentException("Passwords do not match");
        }

        return userService.signUpUser(new User(
            request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                UserRole.GUEST
        ));
    }
}
