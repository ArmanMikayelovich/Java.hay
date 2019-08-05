package am.aca.repository;

import am.aca.entity.ChapterItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterItemRepository extends JpaRepository<ChapterItem, Integer> {
}
