package hay.java.service;

import hay.java.config.Mapper;
import hay.java.dto.UserDto;
import hay.java.entity.UserEntity;
import hay.java.repository.UserRepository;
import hay.java.service.interfaces.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceJpaImpl implements UserService {
    private static final Logger log = LogManager.getLogger(UserServiceJpaImpl.class);

    private final UserRepository userRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceJpaImpl(UserRepository userRepository, Mapper mapper,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto findById(Long userId) {
        return null;

    }


    @Override
    @Transactional
    public UserDto register(UserDto user) {
        log.debug("user registration {} ", user);
        UserEntity userEntity = mapper.map(user, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity = userRepository.save(userEntity);
        log.debug("registration completed. {}", userEntity);
        mapper.map(userEntity, user);
        return user;
    }

    @Override
    @Transactional
    public UserDto update(UserDto user) {
        UserEntity userEntity = mapper.map(user, UserEntity.class);
        userRepository.save(userEntity);
        mapper.map(userEntity, user);
        return user;
    }

    @Override
    @Transactional
    public boolean delete(UserDto user) {
        Optional<UserEntity> byId = userRepository.findById(user.getId());
        if (byId.isPresent()) {
            userRepository.delete(byId.get());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean changePassword(UserDto user, String password) {
        Optional<UserEntity> byId = userRepository.findById(user.getId());
        if (byId.isPresent()) {
            UserEntity userEntity = byId.get();
            if (userEntity.getPassword().equals(user.getPassword())) {
                userEntity.setPassword(password);
                userRepository.saveAndFlush(userEntity);
                return true;
            }
        }
        return false;
    }
}
