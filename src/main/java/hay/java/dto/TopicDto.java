package hay.java.dto;

import hay.java.entity.TopicEntity;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class TopicDto {

    private int topicId;
    @NonNull
    private String topicName;

    public TopicDto(TopicEntity topic) {
        setTopicId(topic.getTopicId());
        setTopicName(topic.getTopicName());
    }

    public TopicDto set(TopicEntity topicEntity) {
        setTopicName(topicEntity.getTopicName());
        setTopicId(topicEntity.getTopicId());
        return this;
    }
}
