package hay.java.dto;

import hay.java.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String password;

    public UserDto(UserEntity userEntity) {
        setId(userEntity.getUserId());
        setFirstName(userEntity.getFirstName());
        setLastName(userEntity.getLastName());
        setEmail(userEntity.getEmail());
        setDateOfBirth(userEntity.getDateOfBirth().toString());
    }

    public static UserDto toDto(UserEntity userEntity) {
        return new UserDto(userEntity);
    }
}
