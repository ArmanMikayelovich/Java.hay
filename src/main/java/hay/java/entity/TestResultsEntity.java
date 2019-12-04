package hay.java.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "test_results", indexes = {
        @Index(name = "user_id_IDX", columnList = "user_id"),
        @Index(name = "user_chapter_IDX", columnList = "user_id,chapter_id")
})
public class TestResultsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ChapterEntity chapterEntity;

    @CreationTimestamp
    @Column(name = "start_time", updatable = false, nullable = false)
    private Date startTime;

    @CreationTimestamp
    @Column(name = "end_time", updatable = false, nullable = false)
    private Date endTime;

    @Column(name = "success_percent", precision = 5, scale = 2)
    private Double successPercent;

}
