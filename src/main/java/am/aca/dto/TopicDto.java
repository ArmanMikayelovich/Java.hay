package am.aca.dto;

import am.aca.entity.ChapterEntity;
import am.aca.entity.TopicEntity;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class TopicDto {

    private int topicId;
    @NonNull
    private String topicName;

    public TopicDto(TopicEntity topic) {
        setTopicId(topic.getTopicID());
        setTopicName(topic.getTopicName());
    }
}
