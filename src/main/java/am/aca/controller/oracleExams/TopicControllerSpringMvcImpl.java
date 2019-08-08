package am.aca.controller.oracleExams;

import am.aca.controller.oracleExams.TopicController;
import am.aca.dto.TopicDto;
import am.aca.entity.TopicEntity;
import am.aca.service.TopicSerice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.function.Predicate;

@Controller
@RequestMapping(value = "oracle-exams/topics")
public class TopicControllerSpringMvcImpl implements TopicController {
    private final TopicSerice topicSerice;

    public TopicControllerSpringMvcImpl(TopicSerice topicSerice) {
        this.topicSerice = topicSerice;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllTopics() {
        ModelAndView model = new ModelAndView("topics");
        model.addObject("topicList", topicSerice.findAll());
        return model;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView postTopic(TopicDto topicDto) {
        if (topicDto.getTopicName() != null && !topicDto.getTopicName().equals("")) {
            topicSerice.save(new TopicEntity(topicDto.getTopicName()));
        }

        return getAllTopics();
    }
}
