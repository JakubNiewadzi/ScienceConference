package pl.mygroup.ScienceConference.user;


public record UserDTO(
        Long id,
        String name,
        String email,
        UserRole role
) {
}
