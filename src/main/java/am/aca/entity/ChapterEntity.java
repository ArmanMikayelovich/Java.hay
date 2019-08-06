package am.aca.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chapters", schema = "oracle_exams",
        indexes = {
                @Index(name = "chapters_chapter_id_uindex",
                        unique = true,
                        columnList = "chapter_id")

        })
@Data
@NoArgsConstructor
public class ChapterEntity {
    public ChapterEntity(TopicEntity topicEntity, String chapterName) {
        setTopicEntity(topicEntity);
        setChapterName(chapterName);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id", nullable = false,updatable = false)
    private int chapterId;

    @Column(name = "chapter_name", length = 256, nullable = false)
    private String chapterName;

    @ManyToOne
    @JoinColumn(name = "topic_id", referencedColumnName = "topic_id", nullable = false)
    private TopicEntity topicEntity;

    @OneToMany(mappedBy = "chapterEntity", orphanRemoval = true)
    private List<ChapterItemEntity> chapterItemList = new ArrayList<>();

    @OneToMany(mappedBy = "chapterEntity", orphanRemoval = true,fetch = FetchType.LAZY)
    private List<QuestionEntity> questionEntityList = new ArrayList<>();
}
