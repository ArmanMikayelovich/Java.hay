package am.aca.service;

import am.aca.dto.ChapterItemDto;
import am.aca.entity.ChapterItemEntity;
import am.aca.repository.ChapterItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChapterItemsServiceJpaImpl implements ChapterItemService {
    private final ChapterItemRepository itemRepo;

    public ChapterItemsServiceJpaImpl(ChapterItemRepository itemRepo) {
        this.itemRepo = itemRepo;
    }

    @Override
    public ChapterItemEntity save(ChapterItemEntity itemEntity) {
        return itemRepo.save(itemEntity);
    }

    @Override
    public Optional<ChapterItemEntity> findOneById(Integer id) {
        return itemRepo.findById(id);
    }

    @Override
    public List<ChapterItemEntity> findAll() {
        return itemRepo.findAll();
    }

    @Override
    public ChapterItemEntity changeHeadline(ChapterItemEntity item, String headline) {
        item.setHeadline(headline);
        return itemRepo.save(item);
    }

    @Override
    public boolean delete(ChapterItemEntity itemEntity) {
        itemRepo.delete(itemEntity);
        return true;
    }

    @Override
    public List<ChapterItemDto> toDto(List<ChapterItemEntity> itemEntityList) {
        return itemEntityList.stream().map(ChapterItemDto::new).collect(Collectors.toList());
    }
}
