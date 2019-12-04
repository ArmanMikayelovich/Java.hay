package hay.java.entity;

import hay.java.dto.ClarificationDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "clarification", /*schema = "oracle_exams",*/
        indexes = {
                @Index(name = "clarification_question_id_IDX",
                        unique = true,
                        columnList = "question_id")
        })
@Data
public class ClarificationEntity {
    public ClarificationEntity(ClarificationDto clarificationDto) {
        setClarificationText(clarificationDto.getText());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "clarification_text", length = 1000, nullable = false)
    private String clarificationText;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id", nullable = false)
    private QuestionEntity questionEntity;


}
