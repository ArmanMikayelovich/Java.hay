package am.aca.service;

import am.aca.dto.TopicDto;
import am.aca.entity.ChapterEntity;
import am.aca.entity.TopicEntity;
import am.aca.repository.TopicRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicServiceJpaImpl implements TopicSerice {
    private static final Logger log = LogManager.getLogger(TopicServiceJpaImpl.class);

    private final TopicRepository topicRepo;
    private final ChapterService chapterService;

    public TopicServiceJpaImpl(TopicRepository topicRepo, ChapterService chapterService) {
        this.topicRepo = topicRepo;
        this.chapterService = chapterService;
    }

    @Override
    public TopicEntity save(TopicEntity topicEntity) {
        log.debug("saving " + topicEntity);
        if (topicEntity == null || topicEntity.getTopicName() == null || topicEntity.getTopicName().equals("")) {
            log.error(topicEntity + "not accepted");
            throw new IllegalArgumentException(topicEntity.toString());
        }
        TopicEntity saved = topicRepo.save(topicEntity);
        log.debug(saved + "successfully saved");
        return saved;
    }

    @Override
    public boolean deleteById(Integer id) {
        Optional<TopicEntity> optional = topicRepo.findById(id);
        if (optional.isPresent()) {
            topicRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(TopicEntity topic) {
        topicRepo.delete(topic);
        return true;
    }

    @Override
    public Optional<TopicEntity> find(Integer id) {
        return topicRepo.findById(id);
    }

    @Override
    public TopicEntity changeName(TopicEntity topic, String name) {
        topic.setTopicName(name);
        return topicRepo.save(topic);
    }


    @Override
    public TopicEntity addChapter(TopicEntity topic, ChapterEntity chapter) {
        topicRepo.save(topic);
        topic.getChapterEntityList().add(chapter);
        chapter.setTopicEntity(topic);
        chapterService.save(chapter);
        return topicRepo.save(topic);
    }

    @Override
    public TopicEntity deleteChapter(TopicEntity topic, ChapterEntity chapter) {
        topic.getChapterEntityList().remove(chapter);
        chapterService.delete(chapter);
        return topicRepo.save(topic);
    }

    @Override
    public List<TopicEntity> findAll() {
        return topicRepo.findAll();
    }

    @Override
    public TopicEntity deleteAllChapters(TopicEntity topicEntity) {
        for (ChapterEntity chapterEntity : topicEntity.getChapterEntityList()) {
            chapterService.delete(chapterEntity);
        }
        return topicRepo.save(topicEntity);
    }

    @Override
    public boolean deleteAllTopics() {
        for (TopicEntity topic : topicRepo.findAll()) {
            deleteAllChapters(topic);
        }
        topicRepo.deleteAll();
        return true;
    }

    @Override
    public TopicDto toDto(TopicEntity topic) {
        return new TopicDto(topic);
    }

    @Override
    public List<TopicDto> toDto(List<TopicEntity> topicList) {
        return topicList.stream().map(TopicDto::new).collect(Collectors.toList());
    }
}
