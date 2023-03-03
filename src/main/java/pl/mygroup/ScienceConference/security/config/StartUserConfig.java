package pl.mygroup.ScienceConference.security.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mygroup.ScienceConference.user.User;
import pl.mygroup.ScienceConference.user.UserRepository;
import pl.mygroup.ScienceConference.user.UserRole;

@Configuration
public class StartUserConfig {

    public StartUserConfig(UserRepository repository, PasswordEncoder encoder){
        User admin = new User();
        admin.setEmail("admin");
        admin.setPassword(encoder.encode("admin"));
        admin.setRole(UserRole.ADMIN);
        repository.save(admin);
    }

}
