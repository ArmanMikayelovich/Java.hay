package am.aca.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions", indexes = {
        @Index(name = "questions_chapter_id_index",
                columnList = "chapter_id"),
        @Index(name = "questions_question_id_uindex",
                unique = true,
                columnList = "question_id")
})
@Data
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int questionId;

    @Column(name = "question_text", length = 1000, nullable = false)
    private String questionText;

    @Column(name = "question_code", length = 1000)
    private String questionCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", referencedColumnName = "chapter_id")
    private ChapterEntity chapterEntity;

    @OneToMany(mappedBy = "questionEntity", orphanRemoval = true)
    private List<AnswerEntity> answerEntityList;

    @OneToOne(mappedBy = "questionEntity", orphanRemoval = true)
    private ClarificationEntity clarificationEntity;
}
