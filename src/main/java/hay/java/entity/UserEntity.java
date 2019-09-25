package hay.java.entity;

import hay.java.service.util.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "user_ID_INDEX", columnList = "user_id")
})
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "user_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false,unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @CreationTimestamp
    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @Column(name = "role", nullable = false)
    private String role = UserRole.USER.toString();

    @Override
    public String toString() {
        return "UserEntity{" +
                "userID=" + userID +
                ", email='" + email + '\'' +
                ", registrationDate=" + registrationDate +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;

        UserEntity that = (UserEntity) o;

        if (getUserID() != that.getUserID()) return false;
        if (getFirstName() != null ? !getFirstName().equals(that.getFirstName()) : that.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(that.getLastName()) : that.getLastName() != null)
            return false;
        if (!getEmail().equals(that.getEmail())) return false;
        if (!getPassword().equals(that.getPassword())) return false;
        if (getDateOfBirth() != null ? !getDateOfBirth().equals(that.getDateOfBirth()) : that.getDateOfBirth() != null)
            return false;
        if (getRegistrationDate() != null ? !getRegistrationDate().equals(that.getRegistrationDate()) : that.getRegistrationDate() != null)
            return false;
        return getRole().equals(that.getRole());
    }

    @Override
    public int hashCode() {
        int result = getUserID();
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + (getDateOfBirth() != null ? getDateOfBirth().hashCode() : 0);
        result = 31 * result + (getRegistrationDate() != null ? getRegistrationDate().hashCode() : 0);
        result = 31 * result + getRole().hashCode();
        return result;
    }
}
