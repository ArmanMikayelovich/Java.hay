package am.aca.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "answers", schema = "oracle_exams",
        indexes = {
                @Index(name = "answers_answer_id_uindex",
                        unique = true,
                        columnList = "answer_id")
        })
@Data
@NoArgsConstructor
public class AnswerEntity {
    public AnswerEntity(QuestionEntity questionEntity,String answerText,String answerCode,boolean accuracy) {
        setQuestionEntity(questionEntity);
        setAnswerText(answerText);
        setAnswerCode(answerCode);
        setAccuracy(accuracy);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private int answerId;

    @Column(name = "answer_text", nullable = false, length = 500)
    private String answerText;

    @Column(name = "answer_code", length = 1, nullable = false)
    private String answerCode;

    @Column(name = "accuracy", nullable = false)
    private boolean accuracy = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id", nullable = false)
    private QuestionEntity questionEntity;
}
