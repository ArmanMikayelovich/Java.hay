package am.aca.contorllerUT.oracleExamsUT;

import am.aca.controller.oracleExams.TopicControllerSpringMvcImpl;
import am.aca.dto.TopicDto;
import am.aca.entity.TopicEntity;
import am.aca.service.TopicService;
import am.aca.service.util.ErrorObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RunWith(MockitoJUnitRunner.class)
public class TopicControllerSpringMvcImplTest {

    private TopicControllerSpringMvcImpl topicController;
    private HttpServletResponse response = new MockHttpServletResponse();
    @Mock
    private TopicService topicService;

    @Before
    public void init() {
        topicController = new TopicControllerSpringMvcImpl(topicService);
    }

    @Test
    public void getAllTopics() {
        topicController.getAllTopics();
        verify(topicService, times(1)).findAll();
    }

    @Test
    public void postTopicWithNullDto() {
        ErrorObject responseObject = (ErrorObject) topicController.postTopic(null, response);
        assertEquals(responseObject.getField(), "TopicDto");
        assertEquals(response.getStatus(), BAD_REQUEST.value());
    }

    @Test
    public void postTopicWithEmptyTopicDto() {
        ErrorObject responseObject = (ErrorObject) topicController.postTopic(new TopicDto(), response);
        assertEquals(responseObject.getField(), "topicName");
        assertEquals(response.getStatus(), BAD_REQUEST.value());
    }

    @Test
    public void postTopicWithNormalTopicDto() {
        TopicDto topicDto = new TopicDto();
        topicDto.setTopicName("name");
        when(topicService.save(any())).thenReturn(new TopicEntity(topicDto));
        Object o = topicController.postTopic(topicDto, response);
        assertTrue(o instanceof TopicDto);
    }


    @Test
    public void deleteTopicWithNullArgument() {
        ErrorObject o = (ErrorObject) topicController.deleteTopic(null, response);

        assertEquals(o.getField(), "TopicDto");
        assertEquals(response.getStatus(), BAD_REQUEST.value());
    }

    @Test
    public void deleteTopicWithIllegalArgument() {
        ErrorObject o =(ErrorObject) topicController.deleteTopic(new TopicDto(), response);
        assertEquals(o.getField(), "topicId");
        assertEquals(response.getStatus(), BAD_REQUEST.value());
    }

    @Test
    public void deleteTopic() {
        TopicDto topic = new TopicDto();
        topic.setTopicId(1);
        topicController.deleteTopic(topic, response);
        verify(topicService, times(1)).deleteById(any());
    }
}