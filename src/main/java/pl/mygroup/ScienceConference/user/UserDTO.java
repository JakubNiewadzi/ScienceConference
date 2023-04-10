package pl.mygroup.ScienceConference.user;


public record UserDTO(
        String name,
        String email,
        UserRole role
) {
}
