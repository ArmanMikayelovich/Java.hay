package hay.java.controller.exams.interfaces;

import hay.java.dto.AnswerDto;
import hay.java.dto.QuestionDto;

import javax.servlet.http.HttpServletResponse;

public interface AnswerController {
    Object createAnswerForQuestion(AnswerDto answer, HttpServletResponse response);

    Object deleteAnswerById(AnswerDto answerDto, HttpServletResponse response);

    Object deleteAllAnswersForQuestion(QuestionDto question, HttpServletResponse response);

    Object getAnswerById(int id, HttpServletResponse response);

    Object getAllAnswersForQuestion(int id, HttpServletResponse response);
}
