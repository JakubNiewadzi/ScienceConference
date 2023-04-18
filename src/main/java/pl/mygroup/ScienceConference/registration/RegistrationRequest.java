package pl.mygroup.ScienceConference.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;


import javax.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String repeatPassword;

}
