package hay.java.controller.exams;

import hay.java.controller.exams.interfaces.ChapterController;
import hay.java.dto.ChapterDto;
import hay.java.dto.TopicDto;
import hay.java.entity.ChapterEntity;
import hay.java.entity.TopicEntity;
import hay.java.service.interfaces.ChapterService;
import hay.java.service.interfaces.TopicService;
import hay.java.service.util.ErrorObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(value = "exams/chapters")
public class ChapterControllerSpringMvcImpl implements ChapterController {
    private final ChapterService chapterService;
    private final TopicService topicService;

    public ChapterControllerSpringMvcImpl(ChapterService chapterService, TopicService topicService) {
        this.chapterService = chapterService;
        this.topicService = topicService;
    }

    /**
     * <b>Path</b> /exams/chapters <br>
     * <b>Method</b> - GET <p>
     * <b>produces</b> JSON
     *
     * @return All chapter from db.
     */
    @Override
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<ChapterDto> getAllChapters() {
        List<ChapterEntity> chapterList = chapterService.findAll();
        return chapterService.toDto(chapterList);
    }

    /**
     * <b>Path</b> /exams/chapters <br>
     * <b>Method</b> - POST <p>
     * <b>produces</b> JSON
     * Return all chapter that have relationship to Topic
     *
     * @param topicId  <b>NonNull</b> with not empty name
     * @param response input from Spring MVC
     * @return if topic found, returns List<ChapterDto>, else  ErrorObject
     * @see ErrorObject
     */
    @Override
    @RequestMapping(value = "/of-topic/{topicId}", method = RequestMethod.GET)
    public Object getAllChaptersOfTopic(@PathVariable("topicId") Integer topicId, HttpServletResponse response) {
        if (topicId < 1) {
            ErrorObject errorObject = new ErrorObject("topicId", "topicId must be > 0");
            response.setStatus(BAD_REQUEST.value());
            return errorObject;
        } else {
            Optional<TopicEntity> optionalTopic = topicService.find(topicId);
            if (optionalTopic.isPresent()) {
                return chapterService.toDto(optionalTopic.get().getChapterEntityList());
            } else {
                ErrorObject errorObject = new ErrorObject("topicId", "Topic with id " + topicId + " not found");
                response.setStatus(NOT_FOUND.value());
                return errorObject;
            }
        }


    }

    /**
     * <b>Path</b> /exams/chapters/{chapterId} <br>
     * <b>Method</b> - GET <p>
     * <b>produces</b> JSON
     * Find chapter by id
     *
     * @param chapterId must be > 0
     * @param response  input from Spring MVC
     * @return if chapter found, returns ChapterDto, else  ErrorObject
     * @see ErrorObject
     */
    @Override
    @RequestMapping(value = "/{chapterId}", method = RequestMethod.GET)
    public Object getChapterById(@PathVariable("chapterId") Integer chapterId, HttpServletResponse response) {
        if (chapterId < 1) {
            ErrorObject errorObject = new ErrorObject("chapterId", "chapter id must be > 0");
            response.setStatus(BAD_REQUEST.value());
            return errorObject;
        }
        Optional<ChapterEntity> optionalChapter = chapterService.findById(chapterId);
        if (optionalChapter.isPresent()) {
            return new ChapterDto(optionalChapter.get());
        }
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return new ErrorObject("chapterId", "Not found.");
    }

    /**
     * <b>Path</b> /exams/chapters/add <br>
     * <b>Method</b> - POST <p>
     * <b>produces</b> JSON
     * create Chapter and attach to topic
     *
     * @param chapter  id must be > 0, name must be not empty
     * @param response input from Spring MVC
     * @return if topic found, returns saved ChapterDto, else  ErrorObject
     * @see ErrorObject
     */
    @Override
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object createChapterOfTopic(@RequestBody ChapterDto chapter, HttpServletResponse response) {
        if (chapter.getTopicId() < 1) {
            ErrorObject errorObject = new ErrorObject("topicId", "topicId must be > 0");
            response.setStatus(BAD_REQUEST.value());
            return errorObject;
        }
        if (chapter.getChapterName().isEmpty()) {
            ErrorObject errorObject = new ErrorObject("chapterName", "chapterName must be not empty");
            response.setStatus(BAD_REQUEST.value());
            return errorObject;
        }

        Optional<TopicEntity> optionalTopicEntity = topicService.find(chapter.getTopicId());
        if (!optionalTopicEntity.isPresent()) {
            ErrorObject errorObject = new ErrorObject("topicId", "Not found Topic with this id: "
                    + chapter.getTopicId());
            response.setStatus(NOT_FOUND.value());
            return errorObject;
        }

        TopicEntity topic = optionalTopicEntity.get();
        ChapterEntity chapterEntity = new ChapterEntity(chapter);
        topicService.addChapter(topic, chapterEntity);
        return chapterService.toDto(topic.getChapterEntityList());

    }

    /**
     * <b>Path</b> /exams/chapters/delete <br>
     * <b>Method</b> - DELETE <p>
     * <b>produces</b> JSON
     * Delete chapter by id.
     *
     * @param chapter  id must be > 0
     * @param response input from Spring MVC
     * @return if chapter found, delete it, and return success message, else  ErrorObject
     * @see ErrorObject
     */
    @Override
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Object deleteChapterById(@RequestBody ChapterDto chapter, HttpServletResponse response) {
        int chapterId = chapter.getChapterId();
        if (chapterId < 1) {
            ErrorObject errorObject = new ErrorObject("chapterId", "chapterId must be > 0");
            response.setStatus(BAD_REQUEST.value());
            return errorObject;
        }

        Optional<ChapterEntity> byId = chapterService.findById(chapterId);
        if (byId.isPresent()) {
            ChapterEntity chapterEntity = byId.get();
            topicService.deleteChapter(chapterEntity.getTopicEntity(), chapterEntity);
            return "Chapter with id " + chapterId + " deleted";
        }

        ErrorObject errorObject = new ErrorObject("chapterId",
                "Chapter with id " + chapter + " not found.");
        response.setStatus(NOT_FOUND.value());
        return errorObject;
    }

    /**
     * <b>Path</b> /exams/chapters/delete-all-of-topic <br>
     * <b>Method</b> - DELETE <p>
     * <b>produces</b> JSON
     * Delete all chapter of topic,
     *
     * @param topic    id must be > 0
     * @param response input from Spring MVC
     * @return if topic found, delete all chapters, and return success message, else  ErrorObject
     * @see ErrorObject
     */
    @Override
    @RequestMapping(value = "/delete-all-of-topic", method = RequestMethod.DELETE)
    public Object deleteAllChaptersOfTopic(@RequestBody TopicDto topic, HttpServletResponse response) {
        int topicId = topic.getTopicId();
        if (topicId < 1) {
            ErrorObject errorObject = new ErrorObject("topicId", "topicId must be > 0");
            response.setStatus(BAD_REQUEST.value());
            return errorObject;
        }
        Optional<TopicEntity> optionalTopicEntity = topicService.find(topicId);
        if (optionalTopicEntity.isPresent()) {
            TopicEntity topicEntity = optionalTopicEntity.get();
            topicService.deleteAllChapters(topicEntity);
            return "all chapter's of " + topicEntity.getTopicName() + " has been deleted";
        }
        ErrorObject errorObject = new ErrorObject("topicId", "Topic with id " + topicId + " not found.");
        response.setStatus(NOT_FOUND.value());
        return errorObject;

    }

    /**
     * <b>Path</b> /exams/chapters/update <br>
     * <b>Method</b> - PUT <p>
     * <b>produces</b> JSON
     * Change name of chapter.
     *
     * @param chapter  id must be > 0,name must be not empty and different than name in DB
     * @param response input from Spring MVC
     * @return if topic found, delete all chapters, and return success message, else  ErrorObject
     * @see ErrorObject
     */
    @Override
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json")
    public Object changeChapterName(ChapterDto chapter, HttpServletResponse response) {
        int chapterId = chapter.getChapterId();
        if (chapterId < 1) {
            ErrorObject errorObject = new ErrorObject("chapterId", "chapters's id must be > 0");
            response.setStatus(BAD_REQUEST.value());
            return errorObject;
        }
        if (chapter.getChapterName() == null || chapter.getChapterName().isEmpty()) {
            ErrorObject errorObject = new ErrorObject("chapterName", "Chapter name is empty.");
            response.setStatus(BAD_REQUEST.value());
            return errorObject;
        }

        Optional<ChapterEntity> byId = chapterService.findById(chapter.getTopicId());
        if (byId.isPresent()) {
            ChapterEntity chapterEntity = byId.get();
            if (chapterEntity.getChapterName().equals(chapter.getChapterName())) {
                response.setStatus(BAD_REQUEST.value());
                return new ErrorObject("chapterName", "Chapter's name equals name in Database.");
            }
            chapterEntity.setChapterName(chapter.getChapterName());
            chapterService.save(chapterEntity);
            return chapter.set(chapterEntity);
        }
        ErrorObject errorObject = new ErrorObject("chapterId",
                "Chapter with id " + chapterId + " not found.");
        response.setStatus(NOT_FOUND.value());
        return errorObject;

    }
}
