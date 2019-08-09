package am.aca.controller.oracleExams;

import am.aca.dto.ChapterDto;
import am.aca.dto.QuestionDto;

import javax.servlet.http.HttpServletResponse;

public interface QuestionController {
    Object createQuestionInChapter(QuestionDto question, HttpServletResponse response);

    Object changeQuestion(QuestionDto question, HttpServletResponse response);

    Object deleteQuestion(QuestionDto question, HttpServletResponse response);

    Object deleteAllQuestionsInChapter(ChapterDto chapter, HttpServletResponse response);

    Object getQuestionById(Integer id, HttpServletResponse response);

    Object getAllQuestionsInChapter(Integer id, HttpServletResponse response);


}
