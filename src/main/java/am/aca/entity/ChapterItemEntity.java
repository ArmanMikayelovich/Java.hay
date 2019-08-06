package am.aca.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public ChapterItemEntity(ChapterEntity chapterEntity, String headline) {
        setChapterEntity(chapterEntity);
        setHeadline(headline);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false,updatable = false)
    private int itemId;

    @Column(name = "headline", length = 1000, nullable = false)
    private String headline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false, referencedColumnName = "chapter_id")
    private ChapterEntity chapterEntity;
}

