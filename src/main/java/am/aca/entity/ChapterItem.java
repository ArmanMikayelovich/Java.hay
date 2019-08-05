package am.aca.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "chapter_items", indexes = {
        @Index(name = "chapter_items_item_id_uindex",
                unique = true,
                columnList = "item_id")
})
@Data
public class ChapterItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private int itemId;

    @Column(name = "headline", length = 1000, nullable = false)
    private String headline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false, referencedColumnName = "chapter_id")
    private ChapterEntity chapterEntity;
}

