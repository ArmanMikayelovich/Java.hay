package am.aca.dto;

import am.aca.entity.TopicEntity;
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
}