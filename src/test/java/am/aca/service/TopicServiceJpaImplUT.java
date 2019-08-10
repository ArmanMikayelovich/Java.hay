package am.aca.service;

import am.aca.entity.TopicEntity;
import am.aca.repository.TopicRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TopicServiceJpaImplUT {
    @Mock
    private TopicRepository topicRepo;
    @Mock
    private ChapterService chapterService;
    @Mock
    private TopicServiceJpaImpl topicService;

    TopicEntity topicEntity;

    @Before
    public void init() {
        topicService = new TopicServiceJpaImpl(topicRepo, chapterService);
    }

    @Test(expected = IllegalArgumentException.class )
    public void saveNullObject() {
        topicService.save(null);
        Mockito.verify(topicRepo, Mockito.times(1)).save(any());
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void find() {
    }

    @Test
    public void changeName() {
    }

    @Test
    public void addChapter() {
    }

    @Test
    public void deleteChapter() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void deleteAllChapters() {
    }

    @Test
    public void deleteAllTopics() {
    }

    @Test
    public void toDto() {
    }

    @Test
    public void testToDto() {
    }
}