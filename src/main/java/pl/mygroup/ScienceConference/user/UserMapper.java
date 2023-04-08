package pl.mygroup.ScienceConference.user;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return  new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getEmail(),
                user.getRole());
    }
}
