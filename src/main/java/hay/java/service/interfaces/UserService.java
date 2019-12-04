package hay.java.service.interfaces;

import hay.java.dto.UserDto;

public interface UserService {
    UserDto register(UserDto user);

    UserDto update(UserDto user);

    boolean delete(UserDto user);

    boolean changePassword(UserDto user, String password);
}
