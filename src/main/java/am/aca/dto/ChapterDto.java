package am.aca.dto;

import am.aca.entity.ChapterEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ChapterDto {
    private int chapterId;
    @NonNull
    private String chapterName;
    @NonNull
    private int topicId;

    public ChapterDto(ChapterEntity chapter) {
        setChapterId(chapter.getChapterId());
        setChapterName(chapter.getChapterName());
        setTopicId(chapter.getTopicEntity().getTopicID());
    }
}
