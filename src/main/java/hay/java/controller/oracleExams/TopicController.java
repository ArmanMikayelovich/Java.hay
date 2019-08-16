package hay.java.controller.oracleExams;

import hay.java.dto.TopicDto;

import javax.servlet.http.HttpServletResponse;

public interface TopicController {
    Object getAllTopics();

    Object postTopic(TopicDto topicDto, HttpServletResponse response);

    Object deleteTopic(TopicDto topic, HttpServletResponse response);

}