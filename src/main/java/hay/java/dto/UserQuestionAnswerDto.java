package hay.java.dto;

import lombok.Data;

@Data
public class UserQuestionAnswerDto {
    private Long id;
    private Long userId;
    private Long chapterId;
    private Long questionId;
    private Long answerId;
}
