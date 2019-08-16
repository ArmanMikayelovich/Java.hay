package hay.java.entityUT;

import hay.java.dto.QuestionDto;
import hay.java.entity.ChapterEntity;
import hay.java.entity.ChapterItemEntity;
import hay.java.entity.QuestionEntity;
import hay.java.entity.TopicEntity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuestionEntityUT {
    private ChapterEntity chapterEntity1 = new ChapterEntity();
    private TopicEntity topicEntity = new TopicEntity();
    private ChapterItemEntity itemEntity = new ChapterItemEntity();
    private QuestionEntity questionEntity1 = new QuestionEntity();
    private QuestionEntity questionEntity2 = new QuestionEntity();

    @Before
    public void init() {
        chapterEntity1.setTopicEntity(topicEntity);
        chapterEntity1.setChapterId(1);
        topicEntity.getChapterEntityList().add(chapterEntity1);
        chapterEntity1.setChapterName("lorem ipsum");
        chapterEntity1.getChapterItemList().add(itemEntity);
        itemEntity.setChapterEntity(chapterEntity1);
        questionEntity1.setChapterEntity(chapterEntity1);
        questionEntity2.setChapterEntity(chapterEntity1);
        questionEntity1.setQuestionText("text");
        questionEntity1.setQuestionCode("text");

    }

    @Test
    public void QuestionEntityTo() {
        QuestionDto questionDto = new QuestionDto(questionEntity1);
        assertEquals(questionDto.getChapterId(), questionEntity1.getChapterEntity().getChapterId());
        assertEquals(questionDto.getId(), questionEntity1.getQuestionId());
        assertEquals(questionDto.getQuestionCode(), questionEntity1.getQuestionCode());
        assertEquals(questionDto.getQuestionText(), questionEntity1.getQuestionText());
    }

    @Test
    public void QuestionDtoToEntity() {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setChapterId(1);
        questionDto.setQuestionCode("lorem ipsum");
        questionDto.setQuestionText("lorem ipsum");
        questionDto.setId(1);
        QuestionEntity questionEntity = new QuestionEntity(questionDto);
        questionEntity.setChapterEntity(chapterEntity1);
        questionEntity.setQuestionId(1);
        assertEquals(questionDto.getId(), questionEntity.getQuestionId());
        assertEquals(questionDto.getQuestionText(), questionEntity.getQuestionText());
        assertEquals(questionDto.getQuestionCode(), questionEntity.getQuestionCode());
        assertEquals(questionDto.getChapterId(), questionEntity.getChapterEntity().getChapterId());
    }

}
