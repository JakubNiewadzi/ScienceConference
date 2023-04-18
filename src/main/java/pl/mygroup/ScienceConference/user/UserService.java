package pl.mygroup.ScienceConference.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "User with email %s not found";
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return repository.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, s)));
    }

    public List<UserDTO> getAllUsers(){
        return repository.findAll().stream()
                .map(userMapper).collect(Collectors.toList());
    }

    public UserDTO getUserDTO(Long id){
        return repository.findById(id)
                .map(userMapper).orElse(null);
    }

    public UserDTO getUserDTO(String email){
        return repository.findByEmail(email)
                .map(userMapper).get();
    }

    public User getUser(Long id){
        return repository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public User getUser(String email){
        return repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No such user in database"));
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
