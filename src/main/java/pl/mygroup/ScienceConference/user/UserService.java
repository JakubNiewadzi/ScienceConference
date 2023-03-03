package pl.mygroup.ScienceConference.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "User with email %s not found";
    private final UserRepository repository;
    private final PasswordEncoder encoder;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return repository.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, s)));
    }

    public String signUpUser(User user){
        boolean userExists = repository.findByEmail(user.getEmail()).isPresent();

        if(userExists){
            throw new IllegalStateException("email already taken");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);

        return "no działa";
    }
}
