package am.aca.service;

import am.aca.entity.ClarificationEntity;
import am.aca.repository.ClarificationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
@Service
public class ClarificationServiceJpaImpl implements ClarificationService {
    private static final Logger log = LogManager.getLogger(ClarificationServiceJpaImpl.class);

    private final ClarificationRepository clarificationRepo;

    public ClarificationServiceJpaImpl(ClarificationRepository clarificationRepo) {
        this.clarificationRepo = clarificationRepo;
    }

    @Override
    public ClarificationEntity save(ClarificationEntity clarificationEntity) {
        log.debug("saving clarification " + clarificationEntity);
        ClarificationEntity save = clarificationRepo.save(clarificationEntity);
        log.debug("clarification saved " + save);
        return save;
    }

    @Override
    public Optional<ClarificationEntity> findOneById(Integer id) {
        log.debug("Searching Clarification by id " + id);

        Optional<ClarificationEntity> byId = clarificationRepo.findById(id);
        if (byId.isPresent()) {
            log.debug("Clarification found. " + byId.get());
        } else {
            log.debug(("Clarification with id " + id + "not found"));
        }
        return byId;
    }

    @Override
    public ClarificationEntity changeText(ClarificationEntity clarificationEntity, String text) {
        log.debug("Changing " + clarificationEntity + "Text to " + text);
        clarificationEntity.setClarificationText(text);
        ClarificationEntity save = clarificationRepo.save(clarificationEntity);
        log.debug("Clarification changed" + save);
        return save;
    }

    @Override
    public boolean deleteById(Integer id) {
        log.debug("Delete clarification by id");
        clarificationRepo.deleteById(id);
        log.debug("Clarification with id " + id+" deleted.");
        return true;
    }

    @Override
    public boolean delete(ClarificationEntity clarificationEntity) {
        log.debug("Deleting clarification" + clarificationEntity);
        clarificationRepo.delete(clarificationEntity);
        log.debug("Deleted clarification" + clarificationEntity);
        return true;
    }

    @Override
    public boolean deleteAll() {
        log.debug("Deleting all Clarifications");
        clarificationRepo.deleteAll();
        log.debug("All clarifications deleted");
        return true;
    }
}
