package am.aca.controller.oracleExams;

import am.aca.dto.ChapterDto;
import am.aca.dto.TopicDto;

import javax.servlet.http.HttpServletResponse;

public interface ChapterController {
    public Object getAllChapters();

    public Object getAllChaptersOfTopic(Integer topicId, HttpServletResponse response);

    public Object getChapterById(Integer chapterId, HttpServletResponse response);

    public Object createChapterOfTopic(ChapterDto chapter, HttpServletResponse response);

    public Object deleteChapterById(ChapterDto chapter, HttpServletResponse response);

    public Object deleteAllChaptersOfTopic(TopicDto topic, HttpServletResponse response);

    public Object changeChapterName(ChapterDto chapter, HttpServletResponse response);


}
