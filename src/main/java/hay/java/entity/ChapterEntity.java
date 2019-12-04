package hay.java.entity;

import hay.java.dto.ChapterDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chapters", /*schema = "oracle_exams",*/
        indexes = {
                @Index(name = "chapters_id_IDX",
                        unique = true,
                        columnList = "id"),
                @Index(name = "chapters_topic_id_IDX",
                        unique = true,
                        columnList = "id,topic_id"),

        })
@Data
@NoArgsConstructor
public class ChapterEntity {

    public ChapterEntity(ChapterDto chapter) {
        setChapterName(chapter.getChapterName());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "chapter_name", length = 256, nullable = false)
    private String chapterName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", referencedColumnName = "topic_id", nullable = false)
    private TopicEntity topicEntity;

    @OneToMany(mappedBy = "chapterEntity", orphanRemoval = true, fetch = FetchType.LAZY,
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<ChapterItemEntity> chapterItemList = new ArrayList<>();

    @OneToMany(mappedBy = "chapterEntity", orphanRemoval = true, fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<QuestionEntity> questionEntityList = new ArrayList<>();

}
