package hay.java.service;

import hay.java.dto.TopicDto;
import hay.java.entity.ChapterEntity;
import hay.java.entity.TopicEntity;

import java.util.List;
import java.util.Optional;

public interface TopicService {
    TopicEntity save(TopicEntity topicEntity);

    boolean deleteById(Integer id);

    boolean delete(TopicEntity topic);

    Optional<TopicEntity> find(int id);

    TopicEntity changeName(TopicEntity topic, String name);

    TopicEntity addChapter(TopicEntity topic, ChapterEntity chapter);

    TopicEntity deleteChapter(TopicEntity topic, ChapterEntity chapter);

    List<TopicEntity> findAll();

    TopicEntity deleteAllChapters(TopicEntity topicEntity);

    boolean deleteAllTopics();

    TopicDto toDto(TopicEntity topic);

    List<TopicDto> toDto(List<TopicEntity> topicList);
}
