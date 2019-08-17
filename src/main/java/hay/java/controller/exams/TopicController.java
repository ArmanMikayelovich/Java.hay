package hay.java.controller.exams;

import hay.java.dto.TopicDto;

import javax.servlet.http.HttpServletResponse;

public interface TopicController {
    Object getAllTopics();

    Object createTopic(TopicDto topicDto, HttpServletResponse response);

    Object deleteTopic(TopicDto topic, HttpServletResponse response);

}
