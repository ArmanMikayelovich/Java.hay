package hay.java.controller.exams;

import hay.java.controller.exams.interfaces.QuestionController;
import hay.java.dto.ChapterDto;
import hay.java.dto.QuestionDto;
import hay.java.entity.ChapterEntity;
import hay.java.entity.QuestionEntity;
import hay.java.service.interfaces.ChapterService;
import hay.java.service.interfaces.QuestionService;
import hay.java.service.util.ErrorObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
@RestController
@RequestMapping(value = "exams/questions")
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
        if (question.getChapterId() < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("chapterId", "chapter id must be > 0");
        }
        if (question.getQuestionText() == null || question.getQuestionText().isEmpty()) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("QuestionTest", "Question text must be not empty.");
        }
        Optional<ChapterEntity> byId = chapterService.findById(question.getChapterId());
        if (byId.isPresent()) {
            ChapterEntity chapterEntity = byId.get();
            QuestionEntity questionEntity = new QuestionEntity(question);
            chapterService.addQuestion(chapterEntity, questionEntity);
            return question.set(questionEntity);
        }
        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("chapterId", "Chapter with id " + question.getChapterId() + " not found.");
    }

    @Override
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json")
    public Object changeQuestion(@RequestBody QuestionDto question, HttpServletResponse response) {
        if (question.getId() < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("questionId", "questionId must be > 0");
        }
        Optional<QuestionEntity> byId = questionService.findById(question.getId());
        if (byId.isPresent()) {
            QuestionEntity questionEntity = byId.get();
            questionEntity.setQuestionCode(question.getQuestionCode());
            questionEntity.setQuestionText(question.getQuestionText());
            questionService.save(questionEntity);
            return question.set(questionEntity);
        }
        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("id", "Question with id " + question.getId() + " not found");
    }

    @Override
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Object deleteQuestion(@RequestBody QuestionDto question, HttpServletResponse response) {
        if (question.getId() < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("id", "id must be > 0");
        }
        Optional<QuestionEntity> byId = questionService.findById(question.getId());
        if (byId.isPresent()) {
            QuestionEntity questionEntity = byId.get();
            chapterService.deleteQuestion(questionEntity.getChapterEntity(), questionEntity);
            return "Question with id" + question.getId() + " deleted.";
        }
        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("id", "question with id " + question.getId() + " not found.");
    }

    @Override
    @RequestMapping(value = "/delete-all-in-chapter", method = RequestMethod.DELETE)
    public Object deleteAllQuestionsInChapter(@RequestBody ChapterDto chapter, HttpServletResponse response) {
        if (chapter.getChapterId() < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("chapterId", "chapter id must be > 0");
        }
        Optional<ChapterEntity> byId = chapterService.findById(chapter.getChapterId());
        if (byId.isPresent()) {
            ChapterEntity chapterEntity = byId.get();
            chapterService.deleteAllQuestions(chapterEntity);
            return "All questions in chapter " + chapter.getChapterId() + " deleted.";
        }
        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("chapterId", "Chapter with id " + chapter.getChapterId() + " not found.");
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getQuestionById(@PathVariable("id") Integer id, HttpServletResponse response) {
        if (id < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("id", "id must be > 0.");
        }
        Optional<QuestionEntity> byId = questionService.findById(id);
        if (byId.isPresent()) {
            QuestionEntity questionEntity = byId.get();
            return new QuestionDto(questionEntity);
        }
        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("id", "Question with id " + id + " not found.");

    }

    @Override
    @RequestMapping(value = "/in-chapter/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllQuestionsInChapter(@PathVariable Integer id, HttpServletResponse response) {
        if (id < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("id", "chapter id must be > 0");
        }
        Optional<ChapterEntity> byId = chapterService.findById(id);
        if (byId.isPresent()) {
            ChapterEntity chapterEntity = byId.get();
            return questionService.toDto(chapterEntity.getQuestionEntityList());
        }
        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("id", "chapter with id " + id + " not found.");
    }
}
