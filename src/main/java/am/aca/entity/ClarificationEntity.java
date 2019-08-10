package am.aca.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "clarification", schema = "oracle_exams",
        indexes = {
                @Index(name = "clarification_question_id_uindex",
                        unique = true,
                        columnList = "question_id")
        })
@Data
@NoArgsConstructor
public class ClarificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clarification_id", nullable = false, unique = true,updatable = false)
    private int clarification_id;

    @Column(name = "clarification_text", length = 1000, nullable = false)
    private String clarificationText;

    @OneToOne
    @JoinColumn(name = "question_id", referencedColumnName = "question_id", nullable = false)
    private QuestionEntity questionEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClarificationEntity)) return false;
        ClarificationEntity that = (ClarificationEntity) o;
        return getClarification_id() == that.getClarification_id() &&
                getClarificationText().equals(that.getClarificationText()) &&
                getQuestionEntity() == null && that.getQuestionEntity() == null ||
                getQuestionEntity().getQuestionId()==that.getQuestionEntity().getQuestionId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClarification_id(), getClarificationText(), getQuestionEntity().getQuestionId());
    }
}
