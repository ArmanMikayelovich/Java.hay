package am.aca.entityUT;

import am.aca.dto.ChapterDto;
import am.aca.entity.ChapterEntity;
import am.aca.entity.ChapterItemEntity;
import am.aca.entity.QuestionEntity;
import am.aca.entity.TopicEntity;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ChapterEntityUT {

    @Test
    public void chapterEntityToChapterDtoTest() {
        ChapterEntity chapterEntity = new ChapterEntity();
        chapterEntity.setChapterName("test name");
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTopicId(1);
        chapterEntity.setTopicEntity(new TopicEntity());
        chapterEntity.setChapterId(1);
        ChapterDto chapterDto = new ChapterDto(chapterEntity);
        assertEquals(chapterDto.getChapterId(), chapterEntity.getChapterId());
        assertEquals(chapterDto.getChapterName(), chapterEntity.getChapterName());
        assertEquals(chapterDto.getTopicId(), chapterEntity.getTopicEntity().getTopicId());
    }

    @Test
    public void chapterDtoToChapterEntityTest() {
        ChapterDto chapterDto = new ChapterDto();
        chapterDto.setChapterName("lorem ipsum");
        ChapterEntity chapterEntity = new ChapterEntity(chapterDto);
        assertEquals(chapterEntity.getChapterName(), chapterDto.getChapterName());
    }

    @Test
    public void chapterItemListTest() {
        ChapterEntity chapterEntity = new ChapterEntity();
        chapterEntity.getChapterItemList().add(new ChapterItemEntity());
        assertFalse(chapterEntity.getChapterItemList().isEmpty());
    }

    @Test
    public void chapterQuestionListTest() {
        ChapterEntity chapterEntity = new ChapterEntity();
        chapterEntity.getQuestionEntityList().add(new QuestionEntity());
        assertFalse(chapterEntity.getQuestionEntityList().isEmpty());
    }

    @Test
    public void ChapterEntityEqualityTest() {
        ChapterEntity chapter1 = new ChapterEntity();
        ChapterEntity chapter2 = new ChapterEntity();
        chapter1.setChapterId(1);
        chapter2.setChapterId(1);
        chapter1.setTopicEntity(new TopicEntity());
        chapter2.setTopicEntity(new TopicEntity());
        chapter1.setChapterName("lorem ipsum");
        chapter2.setChapterName("lorem ipsum");
        chapter1.setQuestionEntityList(new ArrayList<>());
        chapter2.setQuestionEntityList(new ArrayList<>());
        chapter1.setChapterItemList(new ArrayList<>());
        chapter2.setChapterItemList(new ArrayList<>());

        assertEquals(chapter1, chapter2);
    }
}
