package hay.java.repository;

import hay.java.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "select distinct user from UserEntity user " +
            "where user.getId =:userId and user.isActive = true")
    Optional<UserEntity> findById(@Param("userId") Long userId);

    @Query(value = "update UserEntity user " +
            "set user.isActive = false " +
            "where user.getId =:userId")
    void delete(@Param("userId") Long userId);
}
