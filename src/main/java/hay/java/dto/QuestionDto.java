package hay.java.dto;

import hay.java.entity.QuestionEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class QuestionDto {
    private int id;

    @NonNull
    private String questionText;
    @NonNull
    private String questionCode;

    @NonNull
    private int chapterId;


    public QuestionDto(QuestionEntity question) {
        setId(question.getQuestionId());
        setQuestionText(question.getQuestionText());
        setQuestionCode(question.getQuestionCode());
        setChapterId(question.getChapterEntity().getChapterId());
    }

}
