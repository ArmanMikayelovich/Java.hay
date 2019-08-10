package am.aca.controller.oracleExams;

import am.aca.dto.TopicDto;
import am.aca.entity.TopicEntity;
import am.aca.service.TopicService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "oracle-exams/topics")
public class TopicControllerSpringMvcImpl implements TopicController {
    private final TopicService topicService;

    public TopicControllerSpringMvcImpl(TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Object getAllTopics() {
        return topicService.findAll().stream().map(TopicDto::new).collect(Collectors.toList());
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public Object postTopic(@RequestBody TopicDto topicDto) {
        if (topicDto.getTopicName() != null && !topicDto.getTopicName().equals("")) {
            topicService.save(new TopicEntity(topicDto));
        }
        return getAllTopics();
    }

    @Override
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Object deleteTopic(@RequestBody TopicDto topic) {
        int topicId = topic.getTopicId();
        if (topicId >= 0) {
            topicService.deleteById(topicId);
        }
        return getAllTopics();
    }
}
