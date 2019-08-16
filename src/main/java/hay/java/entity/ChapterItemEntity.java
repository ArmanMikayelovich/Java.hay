package hay.java.entity;

import hay.java.dto.ChapterItemDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "chapter_items", schema = "oracle_exams",
        indexes = {
                @Index(name = "chapter_items_item_id_uindex",
                        unique = true,
                        columnList = "item_id")
        })
@Data
@NoArgsConstructor
public class ChapterItemEntity {


    public ChapterItemEntity(ChapterItemDto itemDto) {
        setHeadline(itemDto.getHeadline());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false, updatable = false)
    private int itemId;

    @Column(name = "headline", length = 1000, nullable = false)
    private String headline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false, referencedColumnName = "chapter_id")
    private ChapterEntity chapterEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChapterItemEntity)) return false;
        ChapterItemEntity that = (ChapterItemEntity) o;
        return getItemId() == that.getItemId() &&
                Objects.equals(getHeadline(), that.getHeadline()) &&
                (getChapterEntity() == null || (that.getChapterEntity() != null)
                        && getChapterEntity().getChapterId() == that.getChapterEntity().getChapterId());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemId(), getHeadline(), getChapterEntity().getChapterId());
    }

    @Override
    public String toString() {
        return "ChapterItemEntity{" +
                "itemId=" + itemId +
                ", headline='" + headline + '\'' +
                ", chapterId=" + (chapterEntity != null ? chapterEntity.getChapterId() : " null") +
                '}';
    }
}

