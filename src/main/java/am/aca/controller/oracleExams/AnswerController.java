package am.aca.controller.oracleExams;

import am.aca.dto.AnswerDto;
import am.aca.dto.QuestionDto;

import javax.servlet.http.HttpServletResponse;

public interface AnswerController {
    Object createAnswerForQuestion(AnswerDto answer, HttpServletResponse response);

    Object deleteAnswerById(AnswerDto answerDto, HttpServletResponse response);

    Object deleteAllAnswersForQuestion(QuestionDto question, HttpServletResponse response);

    Object getAnswerById(Integer answerId, HttpServletResponse response);

    Object getAllAnswersForQuestion(Integer questionId, HttpServletResponse response);
}
