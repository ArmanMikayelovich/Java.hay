package am.aca.repository;

import am.aca.entity.ChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository extends JpaRepository<ChapterEntity,Integer> {
}
