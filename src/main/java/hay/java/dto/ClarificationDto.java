package hay.java.dto;

import hay.java.entity.ClarificationEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ClarificationDto {
    private int id;
    @NonNull
    private String text;

    @NonNull
    private int questionId;

    public ClarificationDto(ClarificationEntity clarification) {
        setId(clarification.getClarificationId());
        setText(clarification.getClarificationText());
        setQuestionId(clarification.getQuestionEntity().getQuestionId());
    }
}
