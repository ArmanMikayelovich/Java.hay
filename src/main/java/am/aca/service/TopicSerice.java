package am.aca.service;

import am.aca.dto.TopicDto;
import am.aca.entity.ChapterEntity;
import am.aca.entity.TopicEntity;

import java.util.List;
import java.util.Optional;

public interface TopicSerice {
    TopicEntity save(TopicEntity topicEntity);

    boolean deleteById(Integer id);

    boolean delete(TopicEntity topic);

    Optional<TopicEntity> find(Integer id);

    TopicEntity changeName(TopicEntity topic, String name);

    TopicEntity addChapter(TopicEntity topic, ChapterEntity chapter);

    TopicEntity deleteChapter(TopicEntity topic, ChapterEntity chapter);

    List<TopicEntity> findAll();

    TopicEntity deleteAllChapters(TopicEntity topicEntity);

    boolean deleteAllTopics();

    TopicDto toDto(TopicEntity topic);

    List<TopicDto> toDto(List<TopicEntity> topicList);
}
