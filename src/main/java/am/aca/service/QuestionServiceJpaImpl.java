package am.aca.service;

import am.aca.dto.QuestionDto;
import am.aca.entity.AnswerEntity;
import am.aca.entity.ClarificationEntity;
import am.aca.entity.QuestionEntity;
import am.aca.repository.QuestionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

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
     * Detele Question
     * @param question must be <b>NonNull</b> with legal id <code>question.getQuestionId() > 0</code>
     * @return boolean true, if deleted
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    @Override
    public boolean delete(QuestionEntity question) {
        if (Objects.requireNonNull(question).getQuestionId() < 1) {
            IllegalArgumentException exception = new IllegalArgumentException(Objects.toString(question));
            log.warn(exception);
            throw exception;
        }
        questionRepo.delete(question);
        return true;
    }

    /**
     * Find Question by id
     * @param id must be > 0
     * @return Optional<QuestionEntity>
     * @throws IllegalArgumentException
     */
    @Override
    public Optional<QuestionEntity> findById(int id) {
        if (id < 1) {
            IllegalArgumentException exception = new IllegalArgumentException(id + "must be > 0");
            log.warn(exception);
            throw exception;
        }
        log.debug("Searching Question with id" + id);
        Optional<QuestionEntity> byId = questionRepo.findById(id);
        if (byId.isPresent()) {
            log.debug("Question with id found." + byId.get());
        } else {
            log.debug("Question with" + id + "not found");
        }
        return byId;
    }

    /**
     *
     * @param question must be <b>NonNull</b>
     * @return saved QuestionEntity
     * @throws NullPointerException
     */
    @Override
    public QuestionEntity save(QuestionEntity question) {
        if (Objects.requireNonNull(question).getQuestionText().isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(Objects.toString(question));
            log.warn(exception);
            throw exception;
        }
        log.debug("Saving question " + question);
        QuestionEntity save = questionRepo.save(question);
        log.debug("Question" + save + "saved");
        return save;
    }

    @Override
    public QuestionEntity changeText(QuestionEntity question, String text) {
        log.debug("Changing text of " + question);
        question.setQuestionText(text);
        QuestionEntity save = questionRepo.save(question);
        log.debug("Question text changed " + save);
        return save;
    }

    @Override
    public QuestionEntity changeCode(QuestionEntity question, String code) {
        log.debug("changing code of " + question + " to " + code);
        question.setQuestionCode(code);
        QuestionEntity save = questionRepo.save(question);
        log.debug(save + "changed");
        return save;
    }

    @Override
    public QuestionEntity addAnswer(QuestionEntity question, AnswerEntity answer) {
        log.debug("Adding " + answer + " to " + question);
        questionRepo.save(question);
        question.getAnswerEntityList().add(answer);
        answer.setQuestionEntity(question);
        answerService.save(answer);
        QuestionEntity save = questionRepo.save(question);
        log.debug("Answer" + answer + " added to " + question);
        return save;
    }

    @Override
    public QuestionEntity deleteAnswer(QuestionEntity question, AnswerEntity answer) {
        log.debug("deleting " + answer + " from " + question);
        question.getAnswerEntityList().remove(answer);
        answerService.delete(answer);
        QuestionEntity save = questionRepo.save(question);
        log.debug(answer + "deleted from " + question);
        return save;
    }

    @Override
    public QuestionEntity deleteAllAnswer(QuestionEntity question) {
        log.debug("deleting all answers from " + question);
        for (AnswerEntity answer : question.getAnswerEntityList()) {
            answerService.delete(answer);
        }
        question.setAnswerEntityList(new ArrayList<>());
        QuestionEntity save = questionRepo.save(question);
        log.debug("All answers deleted from " + save);
        return save;
    }

    @Override
    public QuestionEntity changeClarification(QuestionEntity question, ClarificationEntity clarification) {
        log.debug("Changing clarification of " + question + " to " + clarification);
        clarificationService.delete(question.getClarificationEntity());
        question.setClarificationEntity(clarification);
        clarification.setQuestionEntity(question);
        clarificationService.save(clarification);
        QuestionEntity save = questionRepo.save(question);
        log.debug("Clarification of " + question + "changed");
        return save;
    }

    @Override
    public List<QuestionDto> toDto(List<QuestionEntity> questionEntityList) {
        return questionEntityList.stream().map(QuestionDto::new).collect(Collectors.toList());
    }
}
