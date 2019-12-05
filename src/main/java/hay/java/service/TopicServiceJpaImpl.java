package hay.java.service;

import hay.java.config.Mapper;
import hay.java.dto.TopicDto;
import hay.java.entity.TopicEntity;
import hay.java.repository.TopicRepository;
import hay.java.service.interfaces.TopicService;
import hay.java.service.util.exception.TopicNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.util.Objects;

@Service
public class TopicServiceJpaImpl implements TopicService {
    private static final Logger log = LogManager.getLogger(TopicServiceJpaImpl.class);

    private final TopicRepository topicRepo;
    private final Mapper mapper;

    public TopicServiceJpaImpl(TopicRepository topicRepo, Mapper mapper) {
        this.topicRepo = topicRepo;

        this.mapper = mapper;
    }

    @Override
    @Transactional
    public TopicDto save(@Valid TopicDto topicDto) {
        log.debug("saving {}", topicDto);
        TopicEntity topicEntity = mapper.map(topicDto, TopicEntity.class);
        TopicEntity saved = topicRepo.save(topicEntity);
        log.debug(" {} successfully saved", saved);
        mapper.map(topicEntity, topicDto);
        return topicDto;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.debug("deleting topic by id");
        if (Objects.requireNonNull(id) < 1) {
            IllegalArgumentException exception = new IllegalArgumentException("Topic id should be > 0!");
            log.error(exception);
            throw exception;
        }
        topicRepo.deleteById(id);
    }


    @Override
    @Transactional
    public TopicDto find(Long id) {
        if (Objects.requireNonNull(id) < 1) {
            IllegalArgumentException exception = new IllegalArgumentException("id must be > 0, id - " + id);
            log.warn(exception);
            throw exception;
        }
        log.debug("searching topic by id {} ", id);
        TopicEntity topicEntity = topicRepo.findById(id).orElseThrow(TopicNotFoundException::new);

        log.debug("Topic with id {} found: {}", id, topicEntity);
        return mapper.map(topicEntity, TopicDto.class);
    }

    /**
     * @param id   should be  legal id  (id > 0)
     * @param name must be <b>NonNull</b> and not empty
     * @return saved TopicEntity from DB.
     */
    @Override
    @Transactional
    public TopicDto changeName(Long id, String name) {
        if (StringUtils.isEmpty(name) || Objects.requireNonNull(id) < 0) {
            throw new IllegalArgumentException();
        }
        log.debug("Changing name of topic {} | name {}", id, name);
        TopicEntity topicEntity = topicRepo.findById(id).orElseThrow(TopicNotFoundException::new);
        topicEntity.setTopicName(name);
        topicRepo.save(topicEntity);
        log.debug("Successfully changed name of topic {}", topicEntity);

        return mapper.map(topicEntity, TopicDto.class);
    }

    /**
     * @return all TopicEntities from DB.
     */
    @Override
    public Page<TopicDto> findAll(Pageable pageable) {
        log.debug("requested all Topics.");
        return topicRepo.findAll(pageable).map(topicEntity -> mapper.map(topicEntity, TopicDto.class));
    }


    @Override
    @Transactional
    public void deleteAllTopics() {
        topicRepo.deleteAllInBatch();
    }

}
