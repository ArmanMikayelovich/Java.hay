package hay.java.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "chapter_items",/* schema = "oracle_exams",*/
        indexes = {
                @Index(name = "chapter_item_id_IDX",
                        unique = true,
                        columnList = "id")
        })
@Data
public class ChapterItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "headline", length = 1000, nullable = false)
    private String headline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false, referencedColumnName = "chapter_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @EqualsAndHashCode.Exclude
    private ChapterEntity chapterEntity;

}

