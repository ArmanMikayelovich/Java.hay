package am.aca.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions", schema = "oracle_exams",
        indexes = {
                @Index(name = "questions_chapter_id_index",
                        columnList = "chapter_id"),
                @Index(name = "questions_question_id_uindex",
                        unique = true,
                        columnList = "question_id")
        })
@Data
@NoArgsConstructor
public class QuestionEntity implements Serializable {
    public QuestionEntity(ChapterEntity chapterEntity, String questionText) {
        setChapterEntity(chapterEntity);
        setQuestionText(questionText);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id",updatable = false)
    private int questionId;

    @Column(name = "question_text", length = 1000, nullable = false)
    private String questionText;

    @Column(name = "question_code", length = 1000)
    private String questionCode;

    @ManyToOne
    @JoinColumn(name = "chapter_id", referencedColumnName = "chapter_id")
    private ChapterEntity chapterEntity;

    @OneToMany(mappedBy = "questionEntity", orphanRemoval = true,fetch = FetchType.EAGER)
    private List<AnswerEntity> answerEntityList = new ArrayList<>();

    @OneToOne(mappedBy = "questionEntity", orphanRemoval = true)
    private ClarificationEntity clarificationEntity;
}
