package hay.java.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class UserDto {

    @Min(value = 0, message = "User's id must be greater than 0")
    private Long id;
    @NotEmpty(message = "first name should be not empty")
    private String firstName;

    @NotEmpty(message = "first name should be not empty")
    private String lastName;
    @Email(message = "invalid e-mail format")
    private String email;

    private Date dateOfBirth;
    @Size(min = 6, max = 50, message = "password length should be in range 6-50")
    private String password;

    private Date registrationDate;

}
