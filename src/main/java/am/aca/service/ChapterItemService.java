package am.aca.service;

import am.aca.entity.ChapterItemEntity;

import java.util.List;
import java.util.Optional;

public interface ChapterItemService {
    ChapterItemEntity save(ChapterItemEntity itemEntity);

    Optional<ChapterItemEntity> findOneById(Integer id);

    List<ChapterItemEntity> findAll();

    ChapterItemEntity changeHeadline(ChapterItemEntity item, String headline);

    boolean delete(ChapterItemEntity itemEntity);


}
