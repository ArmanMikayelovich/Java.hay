package am.aca.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    public ClarificationEntity(QuestionEntity questionEntity, String clarificationText) {
        setQuestionEntity(questionEntity);
        setClarificationText(clarificationText);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clarification_id", nullable = false, unique = true)
    private int clarification_id;

    @Column(name = "clarification_text", length = 1000, nullable = false)
    private String clarificationText;

    @OneToOne
    @JoinColumn(name = "question_id", referencedColumnName = "question_id", nullable = false)
    private QuestionEntity questionEntity;
}
