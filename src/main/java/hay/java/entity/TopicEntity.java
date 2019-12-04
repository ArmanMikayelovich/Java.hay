package hay.java.entity;

import hay.java.dto.TopicDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topics", /*schema = "oracle_exams",*/
        indexes = {
                @Index(name = "topic_id_IDX",
                        unique = true,
                        columnList = "topic_id")

        })
@Data
@NoArgsConstructor
public class TopicEntity {


    public TopicEntity(TopicDto topicDto) {
        setTopicName(topicDto.getTopicName());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;


    @Column(name = "topic_name", length = 256, nullable = false, unique = true)
    private String topicName;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "topicEntity", fetch = FetchType.LAZY, orphanRemoval = true
            , cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ChapterEntity> chapterEntityList = new ArrayList<>();


}
