package am.aca.service;

import am.aca.dto.ChapterItemDto;
import am.aca.entity.ChapterItemEntity;
import am.aca.repository.ChapterItemRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChapterItemsServiceJpaImpl implements ChapterItemService {
    private static final Logger log = LogManager.getLogger(ChapterItemsServiceJpaImpl.class);

    private final ChapterItemRepository itemRepo;

    public ChapterItemsServiceJpaImpl(ChapterItemRepository itemRepo) {
        this.itemRepo = itemRepo;
    }

    @Override
    public ChapterItemEntity save(ChapterItemEntity itemEntity) {
        log.debug("saving " + itemEntity);
        ChapterItemEntity save = itemRepo.save(itemEntity);
        log.debug("saved " + save);
        return save;
    }

    @Override
    public Optional<ChapterItemEntity> findOneById(Integer id) {
        log.debug("searching ChapterItem by id " + id);
        Optional<ChapterItemEntity> byId = itemRepo.findById(id);
        if (byId.isPresent()) {
            log.debug("ChapterItem with id " + id + "found." + byId.get());
        }else {
            log.debug("ChapterItem with id " + id + "not found.");
        }
        return byId;
    }

    @Override
    public List<ChapterItemEntity> findAll() {

        log.debug("requested All ChapterItems.");
        return itemRepo.findAll();

    }

    @Override
    public ChapterItemEntity changeHeadline(ChapterItemEntity item, String headline) {
        log.debug("changing headline of" + item + " to " + headline);
        item.setHeadline(headline);
        ChapterItemEntity save = itemRepo.save(item);
        log.debug("Headline of " + save + "changed");
        return save;
    }

    @Override
    public boolean delete(ChapterItemEntity itemEntity) {
        log.debug("deleting item " + itemEntity);
        itemRepo.delete(itemEntity);
        log.debug(itemEntity + "deleted");
        return true;
    }

    @Override
    public List<ChapterItemDto> toDto(List<ChapterItemEntity> itemEntityList) {
        return itemEntityList.stream().map(ChapterItemDto::new).collect(Collectors.toList());
    }
}
