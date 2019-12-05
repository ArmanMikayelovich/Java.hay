package hay.java.service.interfaces;

import hay.java.dto.TopicDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicService {

    TopicDto save(TopicDto topicEntity);

    void deleteById(Long id);

    TopicDto find(Long id);

    TopicDto changeName(Long id, String name);


    Page<TopicDto> findAll(Pageable pageable);


    void deleteAllTopics();

}
