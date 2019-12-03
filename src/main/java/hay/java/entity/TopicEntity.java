package hay.java.entity;

import hay.java.dto.TopicDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Column(name = "topic_id", updatable = false)
    private int topicId;


    @Column(name = "topic_name", length = 256, nullable = false,unique = true)
    private String topicName;

    @OneToMany(mappedBy = "topicEntity", fetch = FetchType.LAZY, orphanRemoval = true
            , cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ChapterEntity> chapterEntityList = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TopicEntity)) return false;
        TopicEntity that = (TopicEntity) o;
        return getTopicId() == that.getTopicId() &&
                Objects.equals(getTopicName(), that.getTopicName()) &&
                Objects.equals(getChapterEntityList(), that.getChapterEntityList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTopicId(), getTopicName(), getChapterEntityList());
    }

    @Override
    public String toString() {
        return "TopicEntity{" +
                "topicId=" + topicId +
                ", topicName='" + topicName + '\'' +
                ", chapterEntityList.size=" + chapterEntityList.size() +
                '}';
    }
}
