package hay.java.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class UserQuestionAnswerDto {
    private Long id;
    @Min(value = 0, message = "User's id should be greater than 0")
    private Long userId;
    @Min(value = 0, message = "Chapter's id should be greater than 0")
    private Long chapterId;
    @Min(value = 0, message = "Question's id should be greater than 0")
    private Long questionId;
    @Min(value = 0, message = "Answers's id should be greater than 0")
    private Long answerId;
}
