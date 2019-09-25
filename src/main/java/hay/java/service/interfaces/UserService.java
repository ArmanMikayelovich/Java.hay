package hay.java.service.interfaces;

import hay.java.dto.UserDto;
import hay.java.entity.UserEntity;

public interface UserService {
    UserEntity register(UserDto user,String password);

    boolean update(UserDto user);

    boolean delete(UserDto user);

    boolean changePassword(UserDto user, String password);
}
