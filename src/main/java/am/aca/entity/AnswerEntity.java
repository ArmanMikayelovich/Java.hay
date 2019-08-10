package am.aca.entity;

import am.aca.dto.AnswerDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

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

    public AnswerEntity(AnswerDto answer) {
        setAnswerCode(answer.getCode());
        setAnswerText(answer.getText());
        setAccuracy(answer.isAccuracy());
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id",updatable = false)
    private int answerId;

    @Column(name = "answer_text", nullable = false, length = 500)
    private String answerText;

    @Column(name = "answer_code", length = 1, nullable = false)
    private String answerCode;

    @Column(name = "accuracy", nullable = false)
    private boolean accuracy = false;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private QuestionEntity questionEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnswerEntity)) return false;
        AnswerEntity that = (AnswerEntity) o;
        return getAnswerId() == that.getAnswerId() &&
                isAccuracy() == that.isAccuracy() &&
                getAnswerText().equals(that.getAnswerText()) &&
                getAnswerCode().equals(that.getAnswerCode()) &&
                getQuestionEntity() == null && that.getQuestionEntity() == null ||
                getQuestionEntity().getQuestionId() == that.getQuestionEntity().getQuestionId();
    }

}
