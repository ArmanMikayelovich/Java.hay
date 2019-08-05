package am.aca.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chapters", indexes = {
        @Index(name = "chapters_chapter_id_uindex",
                unique = true,
                columnList = "chapter_id")

})
@Data
public class ChapterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id", nullable = false)
    private int chapterId;

    @Column(name = "chapter_name", length = 256, nullable = false)
    private String chapterName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", referencedColumnName = "topic_id", nullable = false)
    private TopicEntity topicEntity;

    @OneToMany(mappedBy = "chapterEntity", orphanRemoval = true)
    private List<ChapterItem> chapterItemList;

    @OneToMany(mappedBy = "chapterEntity", orphanRemoval = true)
    private List<QuestionEntity> questionEntityList;
}
