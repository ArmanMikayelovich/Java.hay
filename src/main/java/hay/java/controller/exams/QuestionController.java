package hay.java.controller.exams;

import hay.java.dto.ChapterDto;
import hay.java.dto.QuestionDto;

import javax.servlet.http.HttpServletResponse;

public interface QuestionController {
    Object createQuestionInChapter(QuestionDto question, HttpServletResponse response);

    Object changeQuestion(QuestionDto question, HttpServletResponse response);

    Object deleteQuestion(QuestionDto question, HttpServletResponse response);

    Object deleteAllQuestionsInChapter(ChapterDto chapter, HttpServletResponse response);

    Object getQuestionById(Integer id, HttpServletResponse response);

    Object getAllQuestionsInChapter(Integer id, HttpServletResponse response);


}
