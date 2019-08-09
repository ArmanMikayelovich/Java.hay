package am.aca.controller.oracleExams;

import am.aca.dto.ChapterDto;
import am.aca.dto.QuestionDto;
import am.aca.entity.ChapterEntity;
import am.aca.entity.QuestionEntity;
import am.aca.service.ChapterService;
import am.aca.service.QuestionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "oracle-exams/questions")
public class QuestionControllerSpringMvcImpl implements QuestionController {
    private final ChapterService chapterService;
    private final QuestionService questionService;

    public QuestionControllerSpringMvcImpl(ChapterService chapterService, QuestionService questionService) {
        this.chapterService = chapterService;
        this.questionService = questionService;
    }

    @Override
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public Object createQuestionInChapter(@RequestBody QuestionDto question, HttpServletResponse response) {
        ChapterEntity chapterEntity = chapterService.findById(question.getChapterId()).get();
        QuestionEntity questionEntity = new QuestionEntity(question);
        chapterService.addQuestion(chapterEntity, questionEntity);
        return new QuestionDto(questionEntity);
    }

    @Override
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public Object changeQuestion(@RequestBody QuestionDto question, HttpServletResponse response) {
        QuestionEntity questionEntity = questionService.findById(question.getId()).get();
        questionEntity.setQuestionCode(question.getQuestionCode());
        questionEntity.setQuestionText(question.getQuestionText());
        questionService.save(questionEntity);
        return new QuestionDto(questionEntity);
    }

    @Override
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Void deleteQuestion(@RequestBody QuestionDto question, HttpServletResponse response) {
        QuestionEntity questionEntity = questionService.findById(question.getId()).get();
        chapterService.deleteQuestion(questionEntity.getChapterEntity(), questionEntity);
        return null;
    }

    @Override
    @RequestMapping(value = "/delete-all-in-chapter",method = RequestMethod.DELETE)
    public Void deleteAllQuestionsInChapter(@RequestBody ChapterDto chapter, HttpServletResponse response) {
        ChapterEntity chapterEntity = chapterService.findById(chapter.getChapterId()).get();
        chapterService.deleteAllQuestions(chapterEntity);
        return null;
    }

    @Override
    @RequestMapping(value = "/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getQuestionById(@PathVariable("id") Integer id, HttpServletResponse response) {
        QuestionEntity questionEntity = questionService.findById(id).get();
        return new QuestionDto(questionEntity);
    }

    @Override
    @RequestMapping(value = "/in-chapter/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllQuestionsInChapter(@PathVariable Integer id, HttpServletResponse response) {
        ChapterEntity chapterEntity = chapterService.findById(id).get();
        return questionService.toDto(chapterEntity.getQuestionEntityList());
    }
}
