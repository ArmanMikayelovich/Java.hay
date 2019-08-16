package hay.java.service;

import hay.java.dto.ChapterItemDto;
import hay.java.entity.ChapterItemEntity;
import hay.java.repository.ChapterItemRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChapterItemsServiceJpaImpl implements ChapterItemService {
    private static final Logger log = LogManager.getLogger(ChapterItemsServiceJpaImpl.class);

    private final ChapterItemRepository itemRepo;

    public ChapterItemsServiceJpaImpl(ChapterItemRepository itemRepo) {
        this.itemRepo = itemRepo;
    }

    /**
     * Save item to DB.
     *
     * @param itemEntity out item Entity
     * @return saved ChapterItemEntity
     * @throws NullPointerException when argument is null.
     */
    @Override
    public ChapterItemEntity save(ChapterItemEntity itemEntity) {
        log.debug("saving " + itemEntity);
        Objects.requireNonNull(itemEntity);
        ChapterItemEntity save = itemRepo.save(itemEntity);
        log.debug("saved " + save);
        return save;
    }

    /**
     * Find Item entity in DB.
     *
     * @param id must be > 0
     * @return Optional<ChapterItemEntity>
     * @throws IllegalArgumentException when id < 1
     */
    @Override
    public Optional<ChapterItemEntity> findOneById(Integer id) {
        log.debug("searching ChapterItem by id " + id);
        if (id < 1) {
            IllegalArgumentException exception = new IllegalArgumentException("id must be > 0 || id = " + id);
            log.error(exception);
            throw exception;
        }
        Optional<ChapterItemEntity> byId = itemRepo.findById(id);
        if (byId.isPresent()) {
            log.debug("ChapterItem with id " + id + "found." + byId.get());
        } else {
            log.debug("ChapterItem with id " + id + "not found.");
        }
        return byId;
    }

    /**
     * Find all Chapter items in DB.
     * @return List<ChapterItemEntity>
     */
    @Override
    public List<ChapterItemEntity> findAll() {

        log.debug("requested All ChapterItems.");
        return itemRepo.findAll();

    }

    /**
     * Change headline of item.
     * @param item <b>NonNull</b> with legal id( id > 0)
     * @param headline <b>NonNull</b> not empty String
     * @return saved ChapterItemEntity
     * @throws NullPointerException when one of the arguments is null
     * @throws IllegalArgumentException when item's id < 1 or headline is empty String
     */
    @Override
    public ChapterItemEntity changeHeadline(ChapterItemEntity item, String headline) {
        log.debug("changing headline of" + item + " to " + headline);
        if (Objects.requireNonNull(item).getItemId() < 1 |
                Objects.requireNonNull(headline).isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(item + " " + headline);
            log.error(exception);
            throw exception;
        }
        item.setHeadline(headline);
        ChapterItemEntity save = itemRepo.save(item);
        log.debug("Headline of " + save + "changed");
        return save;
    }

    /**
     * Delete ChapterItemEntity from db.
     * @param itemEntity <b>NonNull</b> with legal id (id > 0)
     * @return true, if successfully deleted.
     * @throws NullPointerException if argument is null
     * @throws IllegalArgumentException if id < 1
     */
    @Override
    public boolean delete(ChapterItemEntity itemEntity) {
        log.debug("deleting item " + itemEntity);
        if (Objects.requireNonNull(itemEntity).getItemId() < 1) {
            IllegalArgumentException exception = new IllegalArgumentException(Objects.toString(itemEntity));
            log.error(exception);
            throw exception;
        }
        itemRepo.delete(itemEntity);
        log.debug(itemEntity + "deleted");
        return true;
    }

    @Override
    public List<ChapterItemDto> toDto(List<ChapterItemEntity> itemEntityList) {
        return itemEntityList.stream().map(ChapterItemDto::new).collect(Collectors.toList());
    }
}
