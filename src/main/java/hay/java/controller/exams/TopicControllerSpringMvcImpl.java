package hay.java.controller.exams;

import hay.java.controller.exams.interfaces.TopicController;
import hay.java.dto.TopicDto;
import hay.java.entity.TopicEntity;
import hay.java.service.interfaces.TopicService;
import hay.java.service.util.ErrorObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping(value = "exams/topics")
public class TopicControllerSpringMvcImpl implements TopicController {
    private final TopicService topicService;

    public TopicControllerSpringMvcImpl(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * <b>Path</b> /exams/topics <br>
     * <b>Method</b> - GET <p>
     * <b>produces</b> JSON
     *
     * @return All topics from DB with their id and name
     */
    @Override
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Object getAllTopics() {
        return topicService.findAll().stream().map(TopicDto::new).collect(Collectors.toList());
    }

    /**
     * <b>Path</b> /exams/topics/create <br>
     * <b>Method</b> - POST <p>
     * <b>produces</b> JSON
     *
     * @param topicDto <b>NonNull</b> with not empty name
     * @param response input from Spring MVC
     * @return saved topicDto with ID, or ErrorObject
     * @see ErrorObject
     */
    @Override
    @RequestMapping(value = "/create", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object createTopic(@RequestBody TopicDto topicDto, HttpServletResponse response) {
        if (topicDto == null || topicDto.getTopicId() < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("TopicDto", "Null topicDto");
        }
        if (topicDto.getTopicName() != null && !topicDto.getTopicName().equals("")) {
            TopicEntity save = topicService.save(new TopicEntity(topicDto));
            return topicDto.set(save);
        } else {
            ErrorObject errorObject = new ErrorObject("topicName", "topicName is empty");
            response.setStatus(BAD_REQUEST.value());
            return errorObject;
        }
    }

    /**
     * <b>Path</b> /exams/topics <br>
     * <b>Method</b> - POST <p>
     * <b>produces</b> JSON
     *
     * @param topic    <b>NonNull</b> with not empty name
     * @param response input from Spring MVC
     * @return saved topicDto with ID, or ErrorObject
     * @see ErrorObject
     */
    @Override
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteTopic(@RequestBody TopicDto topic, HttpServletResponse response) {
        if (topic == null) {
            ErrorObject errorObject = new ErrorObject("TopicDto", "topicDto is null");
            response.setStatus(BAD_REQUEST.value());
            return errorObject;
        }
        int topicId = topic.getTopicId();
        if (topicId > 0) {
            topicService.deleteById(topicId);
            return "Successfully deleted";
        } else {
            ErrorObject errorObject = new ErrorObject("topicId", "topicId must be >= 0");
            response.setStatus(BAD_REQUEST.value());
            return errorObject;
        }

    }
}
