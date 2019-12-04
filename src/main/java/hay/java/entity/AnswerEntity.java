package hay.java.entity;

import hay.java.dto.AnswerDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "answers",/* schema = "oracle_exams",*/
        indexes = {
                @Index(name = "answer_id_IDX",
                        unique = true,
                        columnList = "answer_id")
        })
@Data
@NoArgsConstructor
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id", updatable = false)
    private Long answerId;

    @Column(name = "answer_text", nullable = false, length = 500)
    private String answerText;

    @Column(name = "answer_code", length = 1, nullable = false)
    private String answerCode;

    @Column(name = "accuracy", nullable = false)
    private Boolean accuracy = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private QuestionEntity questionEntity;

}
