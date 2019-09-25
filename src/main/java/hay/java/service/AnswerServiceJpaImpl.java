package hay.java.service;

import hay.java.dto.AnswerDto;
import hay.java.entity.AnswerEntity;
import hay.java.repository.AnswerRepository;
import hay.java.service.interfaces.AnswerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerServiceJpaImpl implements AnswerService {
    private static final Logger log = LogManager.getLogger(AnswerServiceJpaImpl.class);

    private final AnswerRepository answerRepo;

    public AnswerServiceJpaImpl(AnswerRepository answerRepo) {
        this.answerRepo = answerRepo;
    }

    /**
     * Save answerEntity to DB.
     *
     * @param answerEntity <b>NonNull</b>
     * @return saved AnswerEntity
     * @throws NullPointerException when argument is null
     */
    @Override
    public AnswerEntity save(AnswerEntity answerEntity) {
        log.debug("saving " + answerEntity);
        Objects.requireNonNull(answerEntity);
        AnswerEntity save = answerRepo.save(answerEntity);
        log.debug(save + "saved");
        return save;
    }

    /**
     * Search AnswerEntity in DB by id.
     *
     * @param id must be > 0
     * @return Optional<AnswerEntity>
     */
    @Override
    public Optional<AnswerEntity> findById(Integer id) {
        log.debug("Searching answer by id " + id);
        if (id < 1) {
            IllegalArgumentException exception = new IllegalArgumentException("id must be > 0 | id = " + id);
            log.error(exception);
            throw exception;
        }
        Optional<AnswerEntity> byId = answerRepo.findById(id);
        if (byId.isPresent()) {
            log.debug("answer with id " + id + "found. " + byId.get());
        } else {
            log.debug("answer with id " + id + "not found");
        }
        return byId;
    }

    /**
     * Find all Answers from DB.
     *
     * @return List<AnswerEntity>
     */
    @Override
    public List<AnswerEntity> findAll() {
        log.debug("Requested All answers.");
        return answerRepo.findAll();
    }

    /**
     * Delete answer
     *
     * @param answerEntity <b>NonNull</b> with legal id (id > 0)
     * @return true if deleted
     * @throws NullPointerException     if argument is null
     * @throws IllegalArgumentException if id < 1
     */
    @Override
    public boolean delete(AnswerEntity answerEntity) {
        log.debug("deleting " + answerEntity);
        if (Objects.requireNonNull(answerEntity).getAnswerId() < 1) {
            IllegalArgumentException exception = new IllegalArgumentException("id must be > 0 | id  = " + answerEntity);
            log.error(exception);
            throw exception;
        }
        answerRepo.delete(answerEntity);
        log.debug(answerEntity + "deleted.");
        return true;
    }

    /**
     * Delete all answers from db.
     *
     * @return true if deleted.
     */
    @Override
    public boolean deleteAll() {
        log.debug("Deleting all answers.");
        answerRepo.deleteAll();
        log.debug("All answers deleted.");
        return true;
    }

    /**
     * Change text of answer
     *
     * @param answer our answer to change
     * @param text   for changing
     * @return save answer
     * @throws NullPointerException     when one of the arguments is null
     * @throws IllegalArgumentException when answer id < 1 or text is empty
     */
    @Override
    public AnswerEntity changeText(AnswerEntity answer, String text) {
        log.debug("Changing text of " + answer + " to " + text);
        if (Objects.requireNonNull(answer).getAnswerId() < 1 | Objects.requireNonNull(text).isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(answer + " " + text);
            log.error(exception);
            throw exception;
        }
        answer.setAnswerText(text);
        AnswerEntity save = answerRepo.save(answer);
        log.debug("Answer changed " + save);
        return save;
    }

    /**
     * Change text of answer
     *
     * @param answer our answer to change
     * @param code   for changing,<b>Mandatory</b> code.length() == 1
     * @return save answer
     * @throws NullPointerException     when one of the arguments is null
     * @throws IllegalArgumentException when answer id < 1 or text is empty
     */
    @Override
    public AnswerEntity changeCode(AnswerEntity answer, String code) {
        log.debug("Changing code of " + answer + " to " + code);
        if (Objects.requireNonNull(answer).getAnswerId() < 1 | Objects.requireNonNull(code).isEmpty()
                | code.length() != 1) {
            IllegalArgumentException exception = new IllegalArgumentException(answer + " " + code);
            log.error(exception);
            throw exception;
        }
        answer.setAnswerCode(code);
        AnswerEntity save = answerRepo.save(answer);
        log.debug("Answer changed " + save);
        return save;
    }

    /**
     * Change accuracy of answer
     *
     * @param answer   <b>NonNull</b> with legal id( id > 0)
     * @param accuracy boolean true/false
     * @return saved AnswerEntity
     */
    @Override
    public AnswerEntity changeAccuracy(AnswerEntity answer, boolean accuracy) {
        log.debug("changing accuracy of " + answer + " to " + accuracy);
        if (Objects.requireNonNull(answer).getAnswerId() < 1) {
            IllegalArgumentException exception = new IllegalArgumentException(Objects.toString(answer));
            log.error(exception);
            throw exception;
        }
        answer.setAccuracy(accuracy);
        AnswerEntity save = answerRepo.save(answer);
        log.debug("Answer changed " + save);
        return save;
    }

    @Override
    public List<AnswerDto> toDto(List<AnswerEntity> answerEntityList) {
        return answerEntityList.stream().map(AnswerDto::new).collect(Collectors.toList());
    }

    public AnswerDto toDto(AnswerEntity answer) {
        AnswerDto answerDto = new AnswerDto(answer);
        return answerDto;
    }
}
