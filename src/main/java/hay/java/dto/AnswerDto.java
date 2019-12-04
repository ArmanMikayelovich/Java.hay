package hay.java.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class AnswerDto {
    @Min(value = 0, message = "id shoudl be greater than 0")
    private Long id;
    @NotEmpty(message = "Answer text is empty.")
    private String answerText;

    @NotEmpty(message = "Answer code is empty.")
    private String answerCode;
    @NotNull
    private Boolean accuracy;

    @Min(value = 0, message = "Question id shoudl be greater than 0")
    private Long questionId;

}
