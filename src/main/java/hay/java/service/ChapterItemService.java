package hay.java.service;

import hay.java.dto.ChapterItemDto;
import hay.java.entity.ChapterItemEntity;

import java.util.List;
import java.util.Optional;

public interface ChapterItemService {
    ChapterItemEntity save(ChapterItemEntity itemEntity);

    Optional<ChapterItemEntity> findOneById(Integer id);

    List<ChapterItemEntity> findAll();

    ChapterItemEntity changeHeadline(ChapterItemEntity item, String headline);

    boolean delete(ChapterItemEntity itemEntity);

    List<ChapterItemDto> toDto(List<ChapterItemEntity> itemEntityList);

}
