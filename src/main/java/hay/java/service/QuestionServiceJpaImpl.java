package hay.java.service;

import hay.java.dto.QuestionDto;
import hay.java.entity.AnswerEntity;
import hay.java.entity.ClarificationEntity;
import hay.java.entity.QuestionEntity;
import hay.java.repository.QuestionRepository;
import hay.java.service.interfaces.AnswerService;
import hay.java.service.interfaces.ClarificationService;
import hay.java.service.interfaces.QuestionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceJpaImpl implements QuestionService {
    private static final Logger log = LogManager.getLogger(QuestionServiceJpaImpl.class);

    private final QuestionRepository questionRepo;
    private final AnswerService answerService;
    private final ClarificationService clarificationService;


    public QuestionServiceJpaImpl(QuestionRepository questionRepo, AnswerService answerService, ClarificationService clarificationService) {
        this.questionRepo = questionRepo;
        this.answerService = answerService;
        this.clarificationService = clarificationService;
    }

    /**
     * Delete Question.
     *
     * @param question must be <b>NonNull</b> with legal id <code>question.getQuestionId() > 0</code>
     * @return boolean true, if deleted
     * @throws NullPointerException     when one of arguments is null.
     * @throws IllegalArgumentException when mandatory not met
     */
    @Override
    @Transactional
    public boolean delete(QuestionEntity question) {
        log.debug("Deleting question " + question);
        if (Objects.requireNonNull(question).getQuestionId() < 1) {
            IllegalArgumentException exception = new IllegalArgumentException(Objects.toString(question));
            log.error(exception);
            throw exception;
        }
        questionRepo.delete(question);
        log.debug("Question with id - " + question.getQuestionId() + "has been deleted");
        return true;
    }

    /**
     * Find Question by id
     *
     * @param id must be > 0
     * @return Optional<QuestionEntity>
     * @throws IllegalArgumentException when mandatory not met
     */
    @Override
    @Transactional
    public Optional<QuestionEntity> findById(int id) {
        log.debug("Searching Question with id" + id);
        if (id < 1) {
            IllegalArgumentException exception = new IllegalArgumentException(id + "must be > 0");
            log.warn(exception);
            throw exception;
        }
        Optional<QuestionEntity> byId = questionRepo.findById(id);
        if (byId.isPresent()) {
            log.debug("Question with id found." + byId.get());
        } else {
            log.debug("Question with" + id + "not found");
        }
        return byId;
    }

    /**
     * Save Question to DB.
     *
     * @param question must be <b>NonNull</b>
     * @return saved QuestionEntity
     * @throws NullPointerException when one of arguments is null.
     */
    @Override
    @Transactional
    public QuestionEntity save(QuestionEntity question) {
        log.debug("Saving question " + question);
        if (Objects.requireNonNull(question).getQuestionText().isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(Objects.toString(question));
            log.error(exception);
            throw exception;
        }
        QuestionEntity save = questionRepo.save(question);
        log.debug("Question" + save + "saved");
        return save;
    }

    /**
     * Changing question text.
     *
     * @param question <b>NonNull</b> QuestionEntity with legal id ( id > 0)
     * @param text     <b>NonNull</b> not empty String
     * @return saved QuestionEntity
     * @throws IllegalArgumentException when mandatory not met
     * @throws NullPointerException     when one of arguments is null.
     */
    @Override
    @Transactional
    public QuestionEntity changeText(QuestionEntity question, String text) {
        log.debug("Changing text of " + question);
        if (Objects.requireNonNull(question).getQuestionId() < 1 | Objects.requireNonNull(text).isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(question + "\t" + text);
            log.debug(exception);
            throw exception;
        }
        question.setQuestionText(text);
        QuestionEntity save = questionRepo.save(question);
        log.debug("Question text changed " + save);
        return save;
    }

    /**
     * Changing question Java code,if have it.
     * <b>Mandatory</b> questionId > 0
     *
     * @param question <b>NonNull</b> QuestionEntity with legal id( id > 0)
     * @param code     <b>NonNull</b> not empty String
     * @return saved QuestionEntity
     * @throws IllegalArgumentException when mandatory not met
     * @throws NullPointerException     when one of arguments is null.
     */
    @Override
    @Transactional
    public QuestionEntity changeCode(QuestionEntity question, String code) {
        log.debug("changing code of " + question + " to " + code);
        if (Objects.requireNonNull(question).getQuestionId() < 1 | Objects.requireNonNull(code).isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(question + "\t" + code);
            log.debug(exception);
            throw exception;
        }
        question.setQuestionCode(code);
        QuestionEntity save = questionRepo.save(question);
        log.debug(save + "changed");
        return save;
    }

    /**
     * Add  AnswerEntity to QuestionEntity.
     * <b>Mandatory</b> question.getQuestionId > 0.
     * <b>Mandatory</b> answer.getAnswerText() !=null && not empty.
     * <b>Mandatory</b> answer.getAnswerCode() !=null && not empty.
     * <b>Mandatory</b> answer.getAnswerCode() must be one character.
     *
     * @param question <b>NonNull</b> QuestionEntity with legal id( id > 0)
     * @param answer   <b>NonNull</b> AnswerEntity
     * @return saved QuestionEntity
     * @throws IllegalArgumentException when mandatory not met
     * @throws NullPointerException     when one of arguments is null
     */
    @Override
    @Transactional
    public QuestionEntity addAnswer(QuestionEntity question, AnswerEntity answer) {
        log.debug("Adding " + answer + " to " + question);
        if (Objects.requireNonNull(question).getQuestionId() < 1 |
                Objects.requireNonNull(answer).getAnswerCode().isEmpty() |
                Objects.requireNonNull(answer).getAnswerCode().length() > 1 |
                Objects.requireNonNull(answer).getAnswerText().isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(question + "\t" + answer);
            log.debug(exception);
            throw exception;
        }
        questionRepo.save(question);
        question.getAnswerEntityList().add(answer);
        answer.setQuestionEntity(question);
        answerService.save(answer);
        QuestionEntity save = questionRepo.save(question);
        log.debug("Answer" + answer + " added to " + question);
        return save;
    }

    /**
     * Delete answer from question's answerList
     * <b>Mandatory</b> <code>question.getQuestionId == answer.getQuestionEntity.getQuestionId()</code>
     *
     * @param question <b>NonNull</b> QuestionEntity with legal id( id > 0)
     * @param answer   <b>NonNull</b> AnswerEntity with legal id ( id > 0)
     * @return saved QuestionEntity
     * @throws IllegalArgumentException when mandatory not met
     * @throws NullPointerException     when one of arguments is null
     */
    @Override
    @Transactional
    public QuestionEntity deleteAnswer(QuestionEntity question, AnswerEntity answer) {
        log.debug("deleting " + answer + " from " + question);
        if (Objects.requireNonNull(question).getQuestionId() < 1 |
                Objects.requireNonNull(answer).getAnswerId() < 1 |
                !question.equals(answer.getQuestionEntity())) {
            IllegalArgumentException exception = new IllegalArgumentException(question + "\t" + answer);
            log.debug(exception);
            throw exception;
        }
        question.getAnswerEntityList().remove(answer);
        answerService.delete(answer);
        QuestionEntity save = questionRepo.save(question);
        log.debug(answer + "deleted from " + question);
        return save;
    }

    /**
     * Add  AnswerEntity to QuestionEntity.
     *
     * @param question <b>NonNull</b> QuestionEntity with legal id( id > 0)
     * @return saved QuestionEntity
     * @throws IllegalArgumentException when mandatory not met
     * @throws NullPointerException     when one of arguments is null
     */
    @Override
    @Transactional
    public QuestionEntity deleteAllAnswer(QuestionEntity question) {
        log.debug("deleting all answers from " + question);
        if (Objects.requireNonNull(question).getQuestionId() < 1) {
            IllegalArgumentException exception = new IllegalArgumentException(Objects.toString(question));
            log.debug(exception);
            throw exception;
        }
        for (AnswerEntity answer : question.getAnswerEntityList()) {
            answerService.delete(answer);
        }
        question.setAnswerEntityList(new ArrayList<>());
        QuestionEntity save = questionRepo.save(question);
        log.debug("All answers deleted from " + save);
        return save;
    }

    /**
     * Add Clarification to question.
     * @param question our current question.
     * @param clarification new clarification to save.
     * @return saved QuestionEntity
     */
    @Override
    @Transactional
    public QuestionEntity changeClarification(QuestionEntity question, ClarificationEntity clarification) {
        log.debug("Changing clarification of " + question + " to " + clarification);
        if (Objects.requireNonNull(question).getQuestionId() < 1 |
                Objects.requireNonNull(clarification).getClarificationText().isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(question + " " + clarification);
            log.error(exception);
            throw exception;
        }
        clarificationService.delete(question.getClarificationEntity());
        question.setClarificationEntity(clarification);
        clarification.setQuestionEntity(question);
        clarificationService.save(clarification);
        QuestionEntity save = questionRepo.save(question);
        log.debug("Clarification of " + question + "changed");
        return save;
    }

    @Override
    public QuestionDto toDto(QuestionEntity questionEntity) {
        return new QuestionDto(questionEntity);
    }

    @Override
    public List<QuestionDto> toDto(List<QuestionEntity> questionEntityList) {
        return questionEntityList.stream().map(QuestionDto::new).collect(Collectors.toList());
    }
}
