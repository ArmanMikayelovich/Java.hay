package hay.java.controller.exams;

import hay.java.controller.exams.interfaces.AnswerController;
import hay.java.dto.AnswerDto;
import hay.java.dto.QuestionDto;
import hay.java.entity.AnswerEntity;
import hay.java.entity.QuestionEntity;
import hay.java.service.interfaces.AnswerService;
import hay.java.service.interfaces.QuestionService;
import hay.java.service.util.ErrorObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/exams/answers")
public class AnswerControllerSpringMcvImpl implements AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    public AnswerControllerSpringMcvImpl(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @Override
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public Object createAnswerForQuestion(AnswerDto answer, HttpServletResponse response) {
        if (answer.getQuestionId() < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("questionId", "questionId must be > 0");
        }
        if (answer.getCode() == null || answer.getCode().isEmpty()) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("code", "Code is empty.");
        }
        if (answer.getText() == null || answer.getText().isEmpty()) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("text", "Text is empty.");
        }
        Optional<QuestionEntity> byId = questionService.findById(answer.getQuestionId());
        if (byId.isPresent()) {
            QuestionEntity questionEntity = byId.get();
            AnswerEntity answerEntity = new AnswerEntity(answer);
            questionService.addAnswer(questionEntity, answerEntity);
            return answerService.toDto(answerEntity);
        }

        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("questionId",
                "Question with id " + answer.getQuestionId() + " not found.");

    }

    @Override
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Object deleteAnswerById(AnswerDto answer, HttpServletResponse response) {
        if (answer.getId() < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("id", "id must be > 0");
        }

        Optional<AnswerEntity> byId = answerService.findById(answer.getId());
        if (byId.isPresent()) {
            AnswerEntity answerEntity = byId.get();
            questionService.deleteAnswer(answerEntity.getQuestionEntity(), answerEntity);
            return "answer with id " + answer.getId() + " deleted.";
        }

        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("id",
                "Answer with id " + answer.getId() + " not found.");
    }

    @Override
    @RequestMapping(value = "/delete-all-in-question", method = RequestMethod.DELETE)
    public Object deleteAllAnswersForQuestion(QuestionDto question, HttpServletResponse response) {
        if (question.getId() < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("id", "id must be > 0");
        }
        Optional<QuestionEntity> byId = questionService.findById(question.getId());
        if (byId.isPresent()) {
            QuestionEntity questionEntity = byId.get();
            questionService.deleteAllAnswer(questionEntity);
            return "All answers for question " + question.getId() + " deleted.";
        }
        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("id", "question with id " + question.getId() + " not found.");
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAnswerById(@PathVariable("id") int id, HttpServletResponse response) {
        if (id < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("id", "Answer with id " + id + " not found.");
        }
        Optional<AnswerEntity> byId = answerService.findById(id);
        if (byId.isPresent()) {
            AnswerEntity answerEntity = byId.get();
            return new AnswerDto(answerEntity);
        }
        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("id", "Answer with id " + id + " not found.");
    }

    @Override
    @RequestMapping(value = "of-question/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllAnswersForQuestion(@PathVariable int id, HttpServletResponse response) {
        if (id < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("id", "id must be > 0");
        }
        Optional<QuestionEntity> byId = questionService.findById(id);
        if (byId.isPresent()) {
            QuestionEntity questionEntity = byId.get();
            return answerService.toDto(questionEntity.getAnswerEntityList());
        }

        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("id", "Question with id " + id + " not found.");
    }
}
