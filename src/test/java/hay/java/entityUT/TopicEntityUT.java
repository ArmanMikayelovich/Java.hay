package hay.java.entityUT;

import hay.java.dto.TopicDto;
import hay.java.entity.ChapterEntity;
import hay.java.entity.TopicEntity;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TopicEntityUT {
    @Test
    public void DtoToEntityTest() {
        TopicDto topicDto = new TopicDto();
        topicDto.setTopicName("test text");
        TopicEntity topicEntity = new TopicEntity(topicDto);
        assertEquals(topicDto.getTopicName(), topicEntity.getTopicName());
    }

    @Test
    public void TopicEntityToDtoTest() {
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTopicName("topic");
        topicEntity.setChapterEntityList(new ArrayList<>());
        topicEntity.setTopicId(1);
        TopicDto topicDto = new TopicDto(topicEntity);

        assertEquals(topicDto.getTopicName(), topicEntity.getTopicName());
        assertEquals(topicDto.getTopicId(), topicEntity.getTopicId());
    }

    @Test
    public void TopicEntitychapterListContentChecking() {
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.getChapterEntityList().add(new ChapterEntity());
        assertFalse(topicEntity.getChapterEntityList().isEmpty());
    }

    @Test
    public void TopicEntityEqualityTest() {
        TopicEntity topic1 = new TopicEntity();
        TopicEntity topic2 = new TopicEntity();
        topic1.setTopicId(1);
        topic2.setTopicId(1);
        topic1.setTopicName("lorem ipsum");
        topic2.setTopicName("lorem ipsum");

        topic1.getChapterEntityList().add(new ChapterEntity());
        topic2.getChapterEntityList().add(new ChapterEntity());
        assertEquals(topic1, topic2);
    }
}
