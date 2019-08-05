package am.aca.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "answers",
        indexes = {
                @Index(name = "answers_answer_id_uindex",
                        unique = true,
                        columnList = "answer_id")
        })
@Data
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private int anwerId;

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
