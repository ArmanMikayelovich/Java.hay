package hay.java.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class QuestionDto {
    private int id;

    @NotEmpty(message = "question text is empty")
    private String questionText;
    @NotEmpty(message = "question code is empty")
    private String questionCode;

    @Min(value = 0, message = "chapter id should be greater than 0")
    private Long chapterId;


}
