package am.aca.controller.oracleExams;

import am.aca.service.ChapterService;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class ChapterControllerSpringMvcImpl implements ChapterController {
    private final ChapterService chapterService;

    public ChapterControllerSpringMvcImpl(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @Override
    public ModelAndView getAllChapters() {
        ModelAndView model = new ModelAndView("chapters");
        List chapterList = chapterService.toDto(chapterService.findAll());
        model.addObject("chapterList", chapterList);
        return model;
    }

    @Override
    public Object getAllChaptersOfTopic(Integer topicId) {
        return null;
    }

    @Override
    public Object getChapterById(Integer chapterId) {
        return null;
    }

    @Override
    public Object createChapterOfTopic(Integer topicId, String chapterName) {
        return null;
    }

    @Override
    public Object deleteChapterById(Integer chapterId) {
        return null;
    }

    @Override
    public Object deleteAllChaptersOfTopic(Integer topicId) {
        return null;
    }
}
