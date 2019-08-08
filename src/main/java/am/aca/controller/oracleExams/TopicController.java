package am.aca.controller.oracleExams;

import am.aca.dto.TopicDto;

public interface TopicController {
    public Object getAllTopics();

    public Object postTopic(TopicDto topicDto);

    public Object deleteTopic(Integer topicId);

}
