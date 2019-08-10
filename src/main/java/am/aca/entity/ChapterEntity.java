package am.aca.entity;

import am.aca.dto.ChapterDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public ChapterEntity(ChapterDto chapter) {
        setChapterName(chapter.getChapterName());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id", nullable = false, updatable = false)
    private int chapterId;

    @Column(name = "chapter_name", length = 256, nullable = false)
    private String chapterName;

    @ManyToOne
    @JoinColumn(name = "topic_id", referencedColumnName = "topic_id", nullable = false)
    private TopicEntity topicEntity;

    @OneToMany(mappedBy = "chapterEntity", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<ChapterItemEntity> chapterItemList = new ArrayList<>();

    @OneToMany(mappedBy = "chapterEntity", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<QuestionEntity> questionEntityList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChapterEntity)) return false;
        ChapterEntity that = (ChapterEntity) o;
        return getChapterId() == that.getChapterId() &&
                Objects.equals(getChapterName(), that.getChapterName()) &&
                getTopicEntity() == null && that.getTopicEntity() == null ||
                getTopicEntity().getTopicId() == getTopicEntity().getTopicId() &&
                        Objects.equals(getChapterItemList(), that.getChapterItemList()) &&
                        Objects.equals(getQuestionEntityList(), that.getQuestionEntityList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChapterId(), getChapterName(), getTopicEntity().getTopicId(), getChapterItemList(), getQuestionEntityList());
    }

    @Override
    public String toString() {
        return "ChapterEntity{" +
                "chapterId=" + chapterId +
                ", chapterName='" + chapterName + '\'' +
                ", topicId=" + topicEntity.getTopicId() +
                ", chapterItemList.size=" + chapterItemList.size() +
                ", questionEntityList.size=" + questionEntityList.size() +
                '}';
    }
}
