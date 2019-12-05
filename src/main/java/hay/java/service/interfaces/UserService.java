package hay.java.service.interfaces;

import hay.java.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserDto> findUsersByName(String name, Pageable pageable);

    UserDto register(UserDto user);

    UserDto update(UserDto user);

    boolean delete(UserDto user);

    boolean changePassword(UserDto user, String password);
}
