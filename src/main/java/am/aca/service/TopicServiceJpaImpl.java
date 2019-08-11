package am.aca.service;

import am.aca.dto.TopicDto;
import am.aca.entity.ChapterEntity;
import am.aca.entity.TopicEntity;
import am.aca.repository.TopicRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicServiceJpaImpl implements TopicService {
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
        if (Objects.requireNonNull(id) < 1) {
            IllegalArgumentException exception = new IllegalArgumentException("Topic id must be > 0!");
            log.warn(exception);
            throw exception;
        }
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
        if (Objects.requireNonNull(topic).getTopicId() < 1) {
            IllegalArgumentException exception = new IllegalArgumentException("Topic id must be > 0");
            log.warn(exception);
            throw exception;
        }
        topicRepo.delete(topic);
        log.debug(topic + " successfully deleted");
        return true;
    }

    @Override
    public Optional<TopicEntity> find(int id) {
        if (id < 1) {
            IllegalArgumentException exception = new IllegalArgumentException("id must be > 0, id - " + id);
            log.warn(exception);
            throw exception;
        }
        log.debug("searching topic by id " + id);
        Optional<TopicEntity> byId = topicRepo.findById(id);
        if (byId.isPresent()) {
            log.debug("Topic with id" + id + "found." + byId.get());
            return byId;
        }
        log.debug("Topic with id " + id + "not found");
        return byId;
    }

    /**
     * @param topic must be <b>NonNull</b> topic with legal id  (id > 0)
     * @param name  must be <b>NonNull</b> and not empty
     * @return saved TopicEntity from DB.
     */
    @Override
    public TopicEntity changeName(TopicEntity topic, String name) {
        log.debug("Changing name of topic" + topic + "to " + name);
        if (Objects.requireNonNull(topic).getTopicId() < 1 | Objects.requireNonNull(name).isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(topic + ", name  - " + name);
            log.warn(exception);
            throw exception;
        }

        topic.setTopicName(name);
        TopicEntity saved = topicRepo.save(topic);
        log.debug("Successfully changed name of topic with id" + topic.getTopicId());
        return saved;
    }

    /**
     * @param topic   must be <b>NonNul</b> TopicEntity with legal id(id> 0)
     * @param chapter must be <b>NonNull</b> ChapterEntity with legal <b>NonNull</b> chapterName(<code>!chapterName.isEmpty()</code>)
     * @return TopicEntity from DB.
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    @Override
    public TopicEntity addChapter(TopicEntity topic, ChapterEntity chapter) {
        log.debug("adding chapter " + chapter + "to Topic " + topic);
        if (Objects.requireNonNull(topic).getTopicId() < 1
                | Objects.requireNonNull(chapter).getChapterName().isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(
                    "topic id must be > 0 && chapter name must be not empty.\n" + topic + chapter);
            log.warn(exception);
            throw exception;
        }
        topicRepo.save(topic);
        topic.getChapterEntityList().add(chapter);
        chapter.setTopicEntity(topic);
        chapterService.save(chapter);
        TopicEntity saved = topicRepo.save(topic);
        log.debug("successfully added " + chapter + "to " + topic);
        return saved;
    }

    /**
     * Deleting chapter from Topic.<br/>
     * <b>Mandatory:</b> <code>topic.getTopicId() == chapter.getTopicEntity().getTopicId()</code>
     *
     * @param topic   must be <b>NonNull</b> with legal id(id > 0)
     * @param chapter must be <b>NonNull</b> with legal id(id > 0)
     * @return TopicEntity saved in DB
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    @Override
    public TopicEntity deleteChapter(TopicEntity topic, ChapterEntity chapter) {
        log.debug("deleting " + chapter + " from " + topic);
        if (Objects.requireNonNull(topic).getTopicId() < 1 |
                Objects.requireNonNull(chapter).getChapterId() < 1 ||
                chapter.getTopicEntity() == null ||
                topic.getTopicId() != chapter.getTopicEntity().getTopicId()) {
            IllegalArgumentException exception = new IllegalArgumentException(topic + " " + chapter);
            log.warn(exception);
            throw exception;
        }
        topic.getChapterEntityList().remove(chapter);
        chapterService.delete(chapter);
        TopicEntity saved = topicRepo.save(topic);
        log.debug("succesfully added " + chapter + " to " + topic);
        return saved;
    }

    /**
     * @return all TopicEntities from DB.
     */
    @Override
    public List<TopicEntity> findAll() {
        log.debug("requested all Topics.");
        return topicRepo.findAll();
    }

    /**
     * @param topicEntity must be <b>NonNull</b> with legal id(id>0)
     * @return
     * @throws NullPointerException     when argument is null
     * @throws IllegalArgumentException when topic id < 0
     */
    @Override
    public TopicEntity deleteAllChapters(TopicEntity topicEntity) {
        if (Objects.requireNonNull(topicEntity).getTopicId() < 1) {
            IllegalArgumentException exception = new IllegalArgumentException(Objects.toString(topicEntity));
            log.warn(exception);
            throw exception;
        }
        for (ChapterEntity chapterEntity : topicEntity.getChapterEntityList()) {
            chapterService.delete(chapterEntity);
        }
        topicEntity.getChapterEntityList().removeAll(topicEntity.getChapterEntityList());
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

    /**
     * Convert TopicEntity to Data Transfer Object
     *
     * @param topic must be <b>NonNull</b> with legal TopicName argument <code>!topicName.isEmpty()</code>
     * @return TopicDto
     * @see TopicDto ,
     */
    @Override
    public TopicDto toDto(TopicEntity topic) {
        if (Objects.requireNonNull(topic).getTopicName().isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(Objects.toString(topic));
            log.warn(exception);
            throw exception;
        }
        return new TopicDto(topic);
    }

    /**
     * Converting List of TopicEntity to List of TopicDto
     *
     * @param topicList
     * @return List<TopicDto>
     * @see TopicDto
     */
    @Override
    public List<TopicDto> toDto(List<TopicEntity> topicList) {
        return topicList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
