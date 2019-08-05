package am.aca.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "clarification", indexes = {
        @Index(name = "clarification_question_id_uindex",
                unique = true,
                columnList = "question_id")
})
@Data
public class ClarificationEntity {
    @Id
    @Column(name = "question_id", nullable = false)
    private int questionId;

    @Column(name = "clarification_text", length = 1000, nullable = false)
    private String clarificationText;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private QuestionEntity questionEntity;
}
