package am.aca.service;

import am.aca.entity.ChapterEntity;
import am.aca.entity.TopicEntity;
import am.aca.repository.TopicRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TopicServiceJpaImplUT {
    @Mock
    private TopicRepository topicRepo;
    @Mock
    private ChapterService chapterService;

    private TopicServiceJpaImpl topicService;


    @Before
    public void init() {
        topicService = new TopicServiceJpaImpl(topicRepo, chapterService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNullObject() {
        topicService.save(null);
        verify(topicRepo, times(1)).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveEmpptyObject() {
        topicService.save(new TopicEntity());
        verify(topicRepo, times(1)).save(any());
    }

    @Test
    public void deleteById() {
        when(topicRepo.findById(1)).thenReturn(Optional.of(new TopicEntity()));
        topicService.deleteById(1);
        verify(topicRepo, times(1)).findById(1);
        verify(topicRepo, times(1)).deleteById(1);
    }

    @Test(expected = NullPointerException.class)
    public void deleteByIdWithNullArgument() {
        topicService.deleteById(null);
        verify(topicRepo, times(1)).findById(any());
        verify(topicRepo, times(0)).deleteById(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteByIdWithMinusNumbert() {
        topicService.deleteById(-1);
        verify(topicRepo, times(0)).findById(any());
        verify(topicRepo, times(0)).deleteById(any());
    }

    @Test
    public void delete() {
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTopicId(1);
        topicService.delete(topicEntity);
        verify(topicRepo, times(1)).delete(topicEntity);
    }

    @Test(expected = NullPointerException.class)
    public void deleteNull() {
        topicService.delete(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteEmptyTopic() {
        topicService.delete(new TopicEntity());
    }

    @Test
    public void find() {
        topicService.find(1);
        verify(topicRepo, times(1)).findById(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findWithZeroArgument() {
        topicService.find(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findWithMinusArgument() {
        topicService.find(Integer.MAX_VALUE + 1);
    }


    @Test(expected = NullPointerException.class)
    public void changeNameWithNullArguments() {
        topicService.changeName(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void changeNameWithNullStringArgument() {
        topicService.changeName(new TopicEntity(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeNameWithEmptyTopicEntity() {
        topicService.changeName(new TopicEntity(), "lorem ipsum");
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeNameWithEmptyString() {
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTopicId(1);
        topicService.changeName(new TopicEntity(), "");
    }

    @Test()
    public void changeNameWithCorrentArguments() {
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTopicId(1);
        topicService.changeName(topicEntity, "lorem ipsum");
        verify(topicRepo, times(1)).save(any());
    }


    @Test(expected = NullPointerException.class)
    public void addChapterWithNullArguments() {
        topicService.addChapter(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void addChapterWithOneNullArgument() {
        topicService.addChapter(new TopicEntity(), null);
    }

    @Test(expected = NullPointerException.class)
    public void addChapterWithIllegalArguments() {
        topicService.addChapter(new TopicEntity(), new ChapterEntity());
    }

    @Test(expected = NullPointerException.class)
    public void addChapterWithIllegalChapterEntityArgument() {
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTopicId(1);
        topicService.addChapter(topicEntity, new ChapterEntity());
    }

    @Test(expected = NullPointerException.class)
    public void addChapterWithIllegalTopicEntityArgument() {
        ChapterEntity chapterEntity = new ChapterEntity();
        chapterEntity.setChapterName("lorem ipsum");
        topicService.addChapter(new TopicEntity(), new ChapterEntity());
    }

    @Test
    public void addChapterWithNormalArguments() {
        ChapterEntity chapterEntity = new ChapterEntity();
        chapterEntity.setChapterName("lorem ipsum");
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTopicId(1);

        topicService.addChapter(topicEntity, chapterEntity);
        verify(chapterService, times(1)).save(any());
        verify(topicRepo, times(2)).save(any());
    }


    @Test(expected = NullPointerException.class)
    public void deleteChapterWithNullArguments() {
        topicService.deleteChapter(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteChapterWithNullTopicEntityArgument() {
        topicService.deleteChapter(null, new ChapterEntity());
    }

    @Test(expected = NullPointerException.class)
    public void deleteChapterWithNullChapterEntityArgument() {
        topicService.deleteChapter(new TopicEntity(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteChapterWithIllegalTopicEntityArgument() {
        ChapterEntity chapterEntity = new ChapterEntity();
        chapterEntity.setChapterId(14);
        topicService.deleteChapter(new TopicEntity(), chapterEntity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteChapterWithIllegalChapterEntityArgument() {
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTopicId(1);
        topicService.deleteChapter(topicEntity, new ChapterEntity());
    }

    @Test(expected = IllegalArgumentException.class )
    public void deleteChapterWithoutMandatory() {
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTopicId(1);
        ChapterEntity chapterEntity = new ChapterEntity();
        chapterEntity.setChapterId(14);
        topicService.deleteChapter(topicEntity,chapterEntity);
    }

    @Test
    public void deleteChapterWithNormalArguments() {
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTopicId(1);
        ChapterEntity chapterEntity = new ChapterEntity();
        chapterEntity.setChapterId(14);
        chapterEntity.setTopicEntity(topicEntity);
        topicService.deleteChapter(topicEntity, chapterEntity);
        verify(chapterService, times(1)).delete(any());
        verify(topicRepo, times(1)).save(any());
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