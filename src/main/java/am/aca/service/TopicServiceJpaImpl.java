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
            throw new IllegalArgumentException(String.valueOf(topicEntity));
        }
        TopicEntity saved = topicRepo.save(topicEntity);
        log.debug(saved + "successfully saved");
        return saved;
    }

    @Override
    public boolean deleteById(Integer id) {
        log.debug("deleting topic by id");
        Optional<TopicEntity> optional = topicRepo.findById(id);

        if (optional.isPresent()) {
            log.debug(" topic by id " + optional.get());
            topicRepo.deleteById(id);
            log.debug(optional.get() + "successfully deleted");
            return true;
        }
        log.warn("Topic with id " + id + "not found");
        return false;
    }

    @Override
    public boolean delete(TopicEntity topic) {
        log.debug("deleting topic" + topic);
        topicRepo.delete(topic);
        log.debug(topic + " successfully deleted");
        return true;
    }

    @Override
    public Optional<TopicEntity> find(Integer id) {
        log.debug("searching topic by id " + id);
        Optional<TopicEntity> byId = topicRepo.findById(id);
        if (byId.isPresent()) {
            log.debug("Topic with id" + id + "found." + byId.get());
            return byId;
        }
        log.debug("Topic with id " + id + "not found");
        return byId;
    }

    @Override
    public TopicEntity changeName(TopicEntity topic, String name) {
        log.debug("Changing name of topic with id " + topic.getTopicId() + "to " + name);
        topic.setTopicName(name);
        TopicEntity saved = topicRepo.save(topic);
        log.debug("Successfully changed name of topic with id" + topic.getTopicId());
        return saved;
    }


    @Override
    public TopicEntity addChapter(TopicEntity topic, ChapterEntity chapter) {
        topicRepo.save(topic);
        log.debug("adding chapter " + chapter + "to Topic " + topic);
        topic.getChapterEntityList().add(chapter);
        chapter.setTopicEntity(topic);
        chapterService.save(chapter);
        TopicEntity saved = topicRepo.save(topic);
        log.debug("successfully added " + chapter + "to " + topic);
        return saved;
    }

    @Override
    public TopicEntity deleteChapter(TopicEntity topic, ChapterEntity chapter) {
        log.debug("deleting " + chapter + " from " + topic);
        topic.getChapterEntityList().remove(chapter);
        chapterService.delete(chapter);
        TopicEntity saved = topicRepo.save(topic);
        log.debug("succesfully added " + chapter + " to " + topic);
        return saved;
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
        log.debug("deleting all topics.");
        for (TopicEntity topic : topicRepo.findAll()) {
            deleteAllChapters(topic);
        }
        topicRepo.deleteAll();
        log.debug("All topics successfully deleted.");
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
