package am.aca.entity;

import am.aca.dto.TopicDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topics", schema = "oracle_exams",
        indexes = {
                @Index(name = "topics_topic_id_uindex",
                        unique = true,
                        columnList = "topic_id")

        })
@Data
@NoArgsConstructor
public class TopicEntity {
    public TopicEntity(String topicName) {
        setTopicName(topicName);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id",updatable = false)
    private int topicId;


    @Column(name = "topic_name", length = 256, nullable = false)
    private String topicName;

    @OneToMany(mappedBy = "topicEntity", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ChapterEntity> chapterEntityList = new ArrayList<>();


}
