package hay.java.dto;

import hay.java.entity.AnswerEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class AnswerDto {
    private int id;
    @NonNull
    private String text;
    @NonNull
    private String code;
    @NonNull
    private boolean accuracy;
    @NonNull
    private int questionId;

    public AnswerDto(AnswerEntity answer) {
        setId(answer.getAnswerId());
        setText(answer.getAnswerText());
        setCode(answer.getAnswerCode());
        setAccuracy(answer.isAccuracy());
        setQuestionId(answer.getQuestionEntity().getQuestionId());
    }

}
