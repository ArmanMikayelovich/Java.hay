package hay.java.controller.oracleExams;

import hay.java.dto.AnswerDto;
import hay.java.dto.QuestionDto;
import hay.java.entity.AnswerEntity;
import hay.java.entity.QuestionEntity;
import hay.java.service.AnswerService;
import hay.java.service.QuestionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/oracle-exams/answers")
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
        QuestionEntity questionEntity = questionService.findById(answer.getQuestionId()).get();
        AnswerEntity answerEntity = new AnswerEntity(answer);
        questionService.addAnswer(questionEntity, answerEntity);
        return new AnswerDto(answerEntity);
    }

    @Override
    @RequestMapping(value = "/delete",method =RequestMethod.DELETE)
    public Void deleteAnswerById(AnswerDto answerDto, HttpServletResponse response) {
        AnswerEntity answerEntity = answerService.findById(answerDto.getId()).get();
        questionService.deleteAnswer(answerEntity.getQuestionEntity(), answerEntity);
        return null;
    }

    @Override
    @RequestMapping(value = "/delete-all-in-question", method = RequestMethod.DELETE)
    public Void deleteAllAnswersForQuestion(QuestionDto question, HttpServletResponse response) {
        QuestionEntity questionEntity = questionService.findById(question.getId()).get();
        questionService.deleteAllAnswer(questionEntity);
        return null;
    }

    @Override
    @RequestMapping(value = "/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAnswerById(Integer answerId, HttpServletResponse response) {
        AnswerEntity answerEntity = answerService.findById(answerId).get();
        return new AnswerDto(answerEntity);
    }

    @Override
    @RequestMapping(value = "of-questions/{questionId}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllAnswersForQuestion(Integer questionId, HttpServletResponse response) {
        QuestionEntity questionEntity = questionService.findById(questionId).get();
        return answerService.toDto(questionEntity.getAnswerEntityList());
    }
}
