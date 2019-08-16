package hay.java.repository;

import hay.java.entity.ClarificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClarificationRepository extends JpaRepository<ClarificationEntity, Integer> {

}
