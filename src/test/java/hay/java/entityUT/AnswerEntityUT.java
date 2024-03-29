package hay.java.entityUT;

import hay.java.dto.AnswerDto;
import hay.java.entity.AnswerEntity;
import hay.java.entity.ChapterEntity;
import hay.java.entity.QuestionEntity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnswerEntityUT {
    private AnswerEntity answerEntity;

    @Before

    public void init() {
        ChapterEntity chapterEntity = new ChapterEntity();
        chapterEntity.setChapterId(1);
        chapterEntity.setChapterName("name");

        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(1);
        questionEntity.setQuestionCode("public static void main(String... args) {}");
        questionEntity.setQuestionText("it is true");

        answerEntity = new AnswerEntity();

        questionEntity.getAnswerEntityList().add(answerEntity);
        answerEntity.setAccuracy(true);
        answerEntity.setAnswerText("true");
        answerEntity.setAnswerCode("A");
        answerEntity.setQuestionEntity(questionEntity);
        answerEntity.setAnswerId(1);
    }

    @Test
    public void answerEntityToDto() {
        AnswerDto answerDto = new AnswerDto(answerEntity);

        assertEquals(answerDto.getCode(), answerEntity.getAnswerCode());
        assertEquals(answerDto.getId(), answerEntity.getAnswerId());
        assertEquals(answerDto.getQuestionId(), answerEntity.getQuestionEntity().getId());
        assertEquals(answerDto.getText(), answerEntity.getAnswerText());
    }
    @Test
    public void answerDtoToEntity() {
        AnswerDto dto = new AnswerDto();
        dto.setAccuracy(true);
        dto.setCode("A");
        dto.setText("lorem ipsum");

        AnswerEntity entity = new AnswerEntity(dto);

        assertEquals(dto.getText(), entity.getAnswerText());
        assertEquals(dto.getCode(), entity.getAnswerCode());
        assertEquals(dto.isAccuracy(), entity.isAccuracy());



    }
}