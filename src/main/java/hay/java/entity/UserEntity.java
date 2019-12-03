package hay.java.entity;

import hay.java.service.util.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "user_email_IDX", columnList = "email"),
        @Index(name = "user_id_IDX", columnList = "user_id"),
})
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "user_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @CreationTimestamp
    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role = UserRole.USER;

    private Boolean isActive = true;
}
