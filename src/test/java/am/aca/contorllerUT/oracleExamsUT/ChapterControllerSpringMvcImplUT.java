package am.aca.contorllerUT.oracleExamsUT;

import am.aca.controller.oracleExams.ChapterControllerSpringMvcImpl;
import am.aca.dto.ChapterDto;
import am.aca.dto.TopicDto;
import am.aca.entity.ChapterEntity;
import am.aca.entity.TopicEntity;
import am.aca.service.ChapterService;
import am.aca.service.TopicService;
import am.aca.service.util.ErrorObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ChapterControllerSpringMvcImplUT {
    private ChapterControllerSpringMvcImpl chapterController;
    private HttpServletResponse response = new MockHttpServletResponse();
    @Mock
    private ChapterService chapterService;
    @Mock
    TopicService topicService;

    @Before
    public void init() {
        chapterController = new ChapterControllerSpringMvcImpl(chapterService, topicService);
    }

    @Test
    public void getAllChapters() {
        when(chapterService.findAll()).thenReturn(new ArrayList<>());
        chapterController.getAllChapters();
        verify(chapterService, times(1)).findAll();
    }

    @Test
    public void getAllChaptersOfTopicBAD_REQUEST() {
        ErrorObject o = (ErrorObject) chapterController.getAllChaptersOfTopic(0, response);
        assertEquals(o.getField(), "topicId");
        assertEquals(response.getStatus(), BAD_REQUEST.value());
    }

    @Test
    public void getAllChaptersOfTopicNOT_FOUND() {
        when(topicService.find(1)).thenReturn(Optional.empty());
        ErrorObject o = (ErrorObject) chapterController.getAllChaptersOfTopic(1, response);
        assertEquals(o.getField(), "topicId");
        assertEquals(response.getStatus(), NOT_FOUND.value());
    }

    @Test
    public void getAllChaptersOfTopic() {
        when(topicService.find(1)).thenReturn(Optional.of(new TopicEntity()));
        Object allChaptersOfTopic = chapterController.getAllChaptersOfTopic(1, response);
        assertTrue(allChaptersOfTopic instanceof List);
    }

    @Test
    public void getChapterByIdBAD_REQUEST() {
        Object o = chapterController.getChapterById(0, response);
        assertTrue(o instanceof ErrorObject);
        assertEquals(((ErrorObject) o).getField(), "chapterId");
        assertEquals(response.getStatus(), BAD_REQUEST.value());
    }

    @Test
    public void getChapterByIdNOT_FOUND() {
        when(chapterService.findById(any())).thenReturn(Optional.empty());
        Object byId = chapterController.getChapterById(1, response);
        assertTrue(byId instanceof ErrorObject);
        assertEquals(((ErrorObject) byId).getField(), "chapterId");
        assertEquals(response.getStatus(), NOT_FOUND.value());
    }

    @Test
    public void getChapterById() {
        ChapterEntity chapterEntity = new ChapterEntity();
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTopicId(1);
        chapterEntity.setTopicEntity(topicEntity);
        chapterEntity.setChapterName("asd");
        when(chapterService.findById(any())).thenReturn(Optional.of(chapterEntity));
        Object byId = chapterController.getChapterById(1, response);
        assertTrue(byId instanceof ChapterDto);
        assertEquals(response.getStatus(), OK.value());
    }

    @Test
    public void createChapterOfTopicBAD_REQUEST_IllegalTopicId() {
        Object o = chapterController.createChapterOfTopic(new ChapterDto(), response);
        assertTrue(o instanceof ErrorObject);
        assertEquals(((ErrorObject) o).getField(), "topicId");
        assertEquals(response.getStatus(), BAD_REQUEST.value());
    }

    @Test
    public void createChapterOfTopicBAD_REQUEST_IllegalChapterName() {
        ChapterDto chapter = new ChapterDto();
        chapter.setTopicId(1);
        chapter.setChapterName("");
        Object o = chapterController.createChapterOfTopic(chapter, response);
        assertTrue(o instanceof ErrorObject);
        assertEquals(((ErrorObject) o).getField(), "chapterName");
        assertEquals(response.getStatus(), BAD_REQUEST.value());
    }

    @Test
    public void createChapterOfTopicNOT_FOUND_IllegalTopicId() {
        when(topicService.find(anyInt())).thenReturn(Optional.empty());
        ChapterDto chapter = new ChapterDto();
        chapter.setTopicId(1);
        chapter.setChapterName("text");
        Object o = chapterController.createChapterOfTopic(chapter, response);
        assertTrue(o instanceof ErrorObject);
        assertEquals(((ErrorObject) o).getField(), "topicId");
        assertEquals(response.getStatus(), NOT_FOUND.value());
    }

    @Test
    public void createChapterOfTopic() {
        when(topicService.find(anyInt())).thenReturn(Optional.of(new TopicEntity()));
        ChapterDto chapter = new ChapterDto();
        chapter.setTopicId(1);
        chapter.setChapterName("text");
        Object o = chapterController.createChapterOfTopic(chapter, response);
        assertTrue(o instanceof List);

    }

    @Test
    public void deleteChapterByIdBAD_REQUEST() {
        Object o = chapterController.deleteChapterById(new ChapterDto(), response);
        assertTrue(o instanceof ErrorObject);
        assertEquals(((ErrorObject) o).getField(), "chapterId");
        assertEquals(response.getStatus(), BAD_REQUEST.value());
    }

    @Test
    public void deleteChapterByIdNOT_FOUND() {
        when(chapterService.findById(any())).thenReturn(Optional.empty());
        ChapterDto chapter = new ChapterDto();
        chapter.setChapterId(1);
        Object o = chapterController.deleteChapterById(chapter, response);
        assertTrue(o instanceof ErrorObject);
        assertEquals(((ErrorObject) o).getField(), "chapterId");
        assertEquals(response.getStatus(), NOT_FOUND.value());
    }

    @Test
    public void deleteChapterById() {
        when(chapterService.findById(any())).thenReturn(Optional.of(new ChapterEntity()));
        ChapterDto chapter = new ChapterDto();
        chapter.setChapterId(1);
       assertEquals(response.getStatus(), OK.value());
    }

    @Test
    public void deleteAllChaptersOfTopicBAD_REQUEST() {
        Object o = chapterController.deleteAllChaptersOfTopic(new TopicDto(), response);
        assertTrue(o instanceof ErrorObject);
        assertEquals(((ErrorObject) o).getField(), "topicId");
        assertEquals(response.getStatus(), BAD_REQUEST.value());
    }
   @Test
    public void deleteAllChaptersOfTopicNOT_FOUND() {
       TopicDto topic = new TopicDto();
       topic.setTopicId(2);
       Object o = chapterController.deleteAllChaptersOfTopic(topic, response);
        assertTrue(o instanceof ErrorObject);
        assertEquals(((ErrorObject) o).getField(), "topicId");
        assertEquals(response.getStatus(), NOT_FOUND.value());
    }
    @Test
    public void deleteAllChaptersOfTopic() {
        when(topicService.find(anyInt())).thenReturn(Optional.of(new TopicEntity()));
       TopicDto topic = new TopicDto();
       topic.setTopicId(2);
       Object o = chapterController.deleteAllChaptersOfTopic(topic, response);
        assertFalse(o instanceof ErrorObject);
        assertEquals(response.getStatus(), OK.value());
    }

    @Test
    public void changeChapterNameBAD_REQUEST_chapterId() {
        Object o = chapterController.changeChapterName(new ChapterDto(), response);
        assertTrue(o instanceof ErrorObject);
        assertEquals(((ErrorObject) o).getField(), "chapterId");
        assertEquals(response.getStatus(), BAD_REQUEST.value());
    }

    @Test
    public void changeChapterNameBAD_REQUEST_chapterName() {
        ChapterDto chapter = new ChapterDto();
        chapter.setChapterId(1);
        Object o = chapterController.changeChapterName(chapter, response);
        assertTrue(o instanceof ErrorObject);
        assertEquals(((ErrorObject) o).getField(), "chapterName");
        assertEquals(response.getStatus(), BAD_REQUEST.value());
    }

    @Test
    public void changeChapterNameNOT_FOUND_chapterId() {
        ChapterDto chapter = new ChapterDto();
        chapter.setChapterId(1);
        chapter.setChapterName("text");
        Object o = chapterController.changeChapterName(chapter, response);
        assertTrue(o instanceof ErrorObject);
        assertEquals(((ErrorObject) o).getField(), "chapterId");
        assertEquals(response.getStatus(), NOT_FOUND.value());
    }

    @Test
    public void changeChapterNameBAD_REQUEST_sameName() {
        ChapterDto chapter = new ChapterDto();
        chapter.setChapterId(1);
        chapter.setChapterName("text");
        ChapterEntity chapterEntity = new ChapterEntity();
        chapterEntity.setChapterName("text");
        when(chapterService.findById(any())).thenReturn(Optional.of(chapterEntity));
        Object o = chapterController.changeChapterName(chapter, response);
        assertTrue(o instanceof ErrorObject);
        assertEquals(((ErrorObject) o).getField(), "chapterName");
        assertEquals(response.getStatus(), BAD_REQUEST.value());
    }

    @Test
    public void changeChapterName() {
        ChapterDto chapter = new ChapterDto();
        chapter.setChapterId(1);
        chapter.setChapterName("text");

        ChapterEntity chapterEntity = new ChapterEntity();
        chapterEntity.setChapterName("Othertext");
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTopicId(1);
        chapterEntity.setTopicEntity(topicEntity);

       lenient().when(chapterService.findById(any())).thenReturn(Optional.of(chapterEntity));
        Object o = chapterController.changeChapterName(chapter, response);
        assertFalse(o instanceof ErrorObject);
        assertEquals(response.getStatus(), OK.value());
    }




}