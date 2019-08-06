package am.aca.service;

import am.aca.entity.AnswerEntity;
import am.aca.entity.ClarificationEntity;
import am.aca.entity.QuestionEntity;
import am.aca.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class QuestionServiceJpaImpl implements QuestionService {
    private final QuestionRepository questionRepo;
    private final AnswerService answerService;
    private final ClarificationService clarificationService;


    public QuestionServiceJpaImpl(QuestionRepository questionRepo, AnswerService answerService, ClarificationService clarificationService) {
        this.questionRepo = questionRepo;
        this.answerService = answerService;
        this.clarificationService = clarificationService;
    }

    @Override
    public boolean delete(QuestionEntity question) {
        questionRepo.delete(question);
        return true;
    }

    @Override
    public QuestionEntity save(QuestionEntity question) {
        return questionRepo.save(question);
    }

    @Override
    public QuestionEntity changeText(QuestionEntity question, String text) {
        question.setQuestionText(text);
        return questionRepo.save(question);
    }

    @Override
    public QuestionEntity changeCode(QuestionEntity question, String code) {
        question.setQuestionCode(code);
        return questionRepo.save(question);
    }

    @Override
    public QuestionEntity addAnswer(QuestionEntity question, AnswerEntity answer) {
        questionRepo.save(question);
        question.getAnswerEntityList().add(answer);
        answer.setQuestionEntity(question);
        answerService.save(answer);
        return questionRepo.save(question);
    }

    @Override
    public QuestionEntity deleteAnswer(QuestionEntity question, AnswerEntity answer) {
        question.getAnswerEntityList().remove(answer);
        answerService.delete(answer);
        return questionRepo.save(question);
    }

    @Override
    public QuestionEntity deleteAllAnswer(QuestionEntity question) {
        for (AnswerEntity answer : question.getAnswerEntityList()) {
            answerService.delete(answer);
        }
        question.setAnswerEntityList(new ArrayList<>());
        return questionRepo.save(question);
    }

    @Override
    public QuestionEntity changeClarification(QuestionEntity question, ClarificationEntity clarification) {
        clarificationService.delete(question.getClarificationEntity());
        question.setClarificationEntity(clarification);
        clarification.setQuestionEntity(question);
        clarificationService.save(clarification);
        return questionRepo.save(question);
    }
}
