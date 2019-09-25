package hay.java.service;

import hay.java.dto.UserDto;
import hay.java.entity.UserEntity;
import hay.java.repository.UserRepository;
import hay.java.service.interfaces.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class UserServiceJpaImpl implements UserService {
    private static final Logger log = LogManager.getLogger(UserServiceJpaImpl.class);

    private UserRepository userRepo;

    public UserServiceJpaImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserEntity register(UserDto user, String password) {
        log.debug("user registration" + user + " password " + password);
        UserEntity entity = new UserEntity();
        entity.setDateOfBirth(Date.valueOf(user.getDateOfBirth()));
        entity.setEmail(user.getEmail());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setPassword(password);
        entity.setDateOfBirth(Date.valueOf(user.getDateOfBirth()));
        UserEntity saved = userRepo.save(entity);
        log.debug("registration completed" + entity);
        return saved;
    }

    @Override
    public boolean update(UserDto user) {
        Optional<UserEntity> byId = userRepo.findById(user.getId());
        if (byId.isPresent()) {
            UserEntity entity = byId.get();
            if (!entity.getFirstName().equals(user.getFirstName())) {
                entity.setFirstName(user.getFirstName());
            }
            if (!entity.getLastName().equals(user.getLastName())) {
                entity.setLastName(user.getLastName());
            }
            if (!entity.getDateOfBirth().toString().equals(user.getDateOfBirth())) {
                entity.setDateOfBirth(Date.valueOf(user.getDateOfBirth()));
            }
            userRepo.save(entity);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(UserDto user) {
        Optional<UserEntity> byId = userRepo.findById(user.getId());
        if (byId.isPresent()) {
            userRepo.delete(byId.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean changePassword(UserDto user, String password) {
        Optional<UserEntity> byId = userRepo.findById(user.getId());
        if (byId.isPresent()) {
            UserEntity userEntity = byId.get();
            if (userEntity.getPassword().equals(user.getPassword())) {
                userEntity.setPassword(password);
                userRepo.saveAndFlush(userEntity);
                return true;
            }
        }
        return false;
    }
}
