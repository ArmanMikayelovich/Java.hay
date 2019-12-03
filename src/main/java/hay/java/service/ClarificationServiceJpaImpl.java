package hay.java.service;

import hay.java.entity.ClarificationEntity;
import hay.java.repository.ClarificationRepository;
import hay.java.service.interfaces.ClarificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class ClarificationServiceJpaImpl implements ClarificationService {
    private static final Logger log = LogManager.getLogger(ClarificationServiceJpaImpl.class);

    private final ClarificationRepository clarificationRepo;

    public ClarificationServiceJpaImpl(ClarificationRepository clarificationRepo) {
        this.clarificationRepo = clarificationRepo;
    }

    /**
     * Save ClarificationEntity to DB.
     *
     * @param clarificationEntity <b>NonNull</b> with not empty clarificationText
     * @return saved ClarificationEntity
     * @throws NullPointerException     when argument is null
     * @throws IllegalArgumentException when clarificationText is empty
     */
    @Override
    @Transactional
    public ClarificationEntity save(ClarificationEntity clarificationEntity) {
        log.debug("saving clarification " + clarificationEntity);
        if (Objects.requireNonNull(clarificationEntity).getClarificationText().isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(
                    "Clarification text must be not empty " + clarificationEntity);
            log.error(exception);
            throw exception;
        }
        ClarificationEntity save = clarificationRepo.save(clarificationEntity);
        log.debug("clarification saved " + save);
        return save;
    }

    /**
     * Find Clarification by id
     *
     * @param id must be > 0
     * @return Optional<ClarificationEntity>
     * @throws IllegalArgumentException when id < 1.
     */
    @Override
    @Transactional
    public Optional<ClarificationEntity> findOneById(Integer id) {
        log.debug("Searching Clarification by id " + id);
        if (id < 1) {
            IllegalArgumentException exception = new IllegalArgumentException("id must be > 0");
            log.error(exception);
            throw exception;
        }
        Optional<ClarificationEntity> byId = clarificationRepo.findById(id);
        if (byId.isPresent()) {
            log.debug("Clarification found. " + byId.get());
        } else {
            log.debug(("Clarification with id " + id + "not found"));
        }
        return byId;
    }

    /**
     * change text of Clarification.
     *
     * @param clarificationEntity <b>NonNull</b> with legal id (lid > 0)
     * @param text                <b>NonNull</b> not empty String
     * @return saved ClarificationEntity
     * @throws NullPointerException     when one of the arguments is null
     * @throws IllegalArgumentException when clarification id < 1 or text is empty
     */
    @Override
    @Transactional
    public ClarificationEntity changeText(ClarificationEntity clarificationEntity, String text) {
        log.debug("Changing " + clarificationEntity + "Text to " + text);
        if (Objects.requireNonNull(clarificationEntity).getClarificationId() < 1 |
                Objects.requireNonNull(text).isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(clarificationEntity + " " + text);
            log.error(exception);
            throw exception;
        }
        clarificationEntity.setClarificationText(text);
        ClarificationEntity save = clarificationRepo.save(clarificationEntity);
        log.debug("Clarification changed" + save);
        return save;
    }

    /**
     * Delete Clarification by id
     *
     * @param id must be > 0
     * @return true, if successfully deleted
     * @throws IllegalArgumentException when id < 1
     */
    @Override
    @Transactional
    public boolean deleteById(Integer id) {
        log.debug("Delete clarification by id");
        if (id < 1) {
            IllegalArgumentException exception = new IllegalArgumentException("id must be > 0 || id  = " + id);
            log.error(exception);
            throw exception;
        }
        clarificationRepo.deleteById(id);
        log.debug("Clarification with id " + id + " deleted.");
        return true;
    }

    /**
     * Delete clarification from DB.
     *
     * @param clarificationEntity <b>NonNull</b>
     * @return true, if successfully deleted.
     * @throws NullPointerException     when argument is null.
     * @throws IllegalArgumentException when id < 1
     */
    @Override
    @Transactional
    public boolean delete(ClarificationEntity clarificationEntity) {
        log.debug("Deleting clarification" + clarificationEntity);
        if (Objects.requireNonNull(clarificationEntity).getClarificationId() < 1) {
            IllegalArgumentException exception =
                    new IllegalArgumentException(Objects.toString(clarificationEntity));
            log.error(exception);
            throw exception;
        }
        clarificationRepo.delete(clarificationEntity);
        log.debug("Deleted clarification" + clarificationEntity);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteAll() {
        log.debug("Deleting all Clarifications");
        clarificationRepo.deleteAll();
        log.debug("All clarifications deleted");
        return true;
    }
}
