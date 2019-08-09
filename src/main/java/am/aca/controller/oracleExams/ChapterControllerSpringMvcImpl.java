package am.aca.controller.oracleExams;

import am.aca.dto.ChapterDto;
import am.aca.dto.TopicDto;
import am.aca.entity.ChapterEntity;
import am.aca.entity.TopicEntity;
import am.aca.service.ChapterService;
import am.aca.service.TopicSerice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "oracle-exams/chapters")
public class ChapterControllerSpringMvcImpl implements ChapterController {
    private final ChapterService chapterService;
    private final TopicSerice topicSerice;

    public ChapterControllerSpringMvcImpl(ChapterService chapterService, TopicSerice topicSerice) {
        this.chapterService = chapterService;
        this.topicSerice = topicSerice;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public List<ChapterDto> getAllChapters() {
        List<ChapterEntity> chapterList = chapterService.findAll();
        return chapterService.toDto(chapterList);
    }


    @Override
    @RequestMapping(value = "/of-topic/{topicId}",method = RequestMethod.GET)
    public Object getAllChaptersOfTopic(@PathVariable("topicId") Integer topicId,HttpServletResponse response) {
        if (topicId >= 0) {
            Optional<TopicEntity> optionalTopic = topicSerice.find(topicId);
            if (optionalTopic.isPresent()) {
                return chapterService.toDto(optionalTopic.get().getChapterEntityList());
            }
        }
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return Collections.emptyList();

    }

    @Override
    @RequestMapping(value = "/{chapterId}",method = RequestMethod.GET)
    public Object getChapterById(@PathVariable("chapterId") Integer chapterId, HttpServletResponse response) {
        Optional<ChapterEntity> optionalChapter = chapterService.findById(chapterId);

        if (optionalChapter.isPresent()) {
            return chapterService.toDto(optionalChapter.get());
        }
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return new ChapterDto();
    }

    @Override
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object createChapterOfTopic(@RequestBody ChapterDto chapter, HttpServletResponse response) {
        if (chapter.getTopicId() >= 0 && !chapter.getChapterName().equals("")) {
            TopicEntity topic = topicSerice.find(chapter.getTopicId()).get();
            ChapterEntity chapterEntity = new ChapterEntity(chapter);
            topicSerice.addChapter(topic, chapterEntity);
            return chapterService.toDto(topic.getChapterEntityList());
        }
         response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return null;
    }

    @Override
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Object deleteChapterById(@RequestBody ChapterDto chapter,HttpServletResponse response) {
        int chapterId = chapter.getChapterId();
        ChapterEntity chapterEntity = chapterService.findById(chapterId).get();
        topicSerice.deleteChapter(chapterEntity.getTopicEntity(), chapterEntity);
        return new ChapterDto(chapterEntity) + "deleted";
    }

    @Override
    @RequestMapping(value = "/delete-all-of-topic",method = RequestMethod.DELETE)
    public Object deleteAllChaptersOfTopic(@RequestBody TopicDto topic,HttpServletResponse response) {
        int topicId = topic.getTopicId();
        if (topicId >= 0) {
            TopicEntity topicEntity = topicSerice.find(topicId).get();
            topicSerice.deleteAllChapters(topicEntity);
            return "all chapter's of " + topicEntity.getTopicName() + " has been deleted";
        }
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return null;
    }

    @Override
    public Object changeChapterName(ChapterDto chapter, HttpServletResponse response) {
        ChapterEntity chapterEntity = chapterService.findById(chapter.getTopicId()).get();
        chapterEntity.setChapterName(chapter.getChapterName());
        chapterService.save(chapterEntity);
        return new ChapterDto(chapterEntity);
    }
}
