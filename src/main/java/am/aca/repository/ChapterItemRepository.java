package am.aca.repository;

import am.aca.entity.ChapterItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterItemRepository extends JpaRepository<ChapterItemEntity, Integer> {
}
