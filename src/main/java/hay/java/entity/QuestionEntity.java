package hay.java.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions", /*schema = "oracle_exams",*/
        indexes = {
                @Index(name = "questions_chapter_id_IDX",
                        columnList = "chapter_id"),
                @Index(name = "question_id_IDX",
                        unique = true,
                        columnList = "id")
        })
@Data
@NoArgsConstructor
public class QuestionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "question_text", length = 1000, nullable = false)
    private String questionText;

    @Column(name = "question_code", length = 1000)
    private String questionCode;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", referencedColumnName = "chapter_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ChapterEntity chapterEntity;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "questionEntity", orphanRemoval = true,
            fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<AnswerEntity> answerEntityList = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "questionEntity", orphanRemoval = true,
            fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private ClarificationEntity clarificationEntity;


}
