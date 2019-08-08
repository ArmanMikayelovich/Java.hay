package am.aca.controller.oracleExams;

public interface ChapterController {
    public Object getAllChapters();

    public Object getAllChaptersOfTopic(Integer topicId);

    public Object getChapterById(Integer chapterId);

    public Object createChapterOfTopic(Integer topicId, String chapterName);

    public Object deleteChapterById(Integer chapterId);

    public Object deleteAllChaptersOfTopic(Integer topicId);


}
