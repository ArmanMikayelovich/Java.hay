package hay.java.entityUT;

import hay.java.dto.ClarificationDto;
import hay.java.entity.ClarificationEntity;
import hay.java.entity.QuestionEntity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClarificationEntityUT {
    private ClarificationEntity clarificationEntity;
    private QuestionEntity questionEntity;


    @Before
    public void init() {
        clarificationEntity = new ClarificationEntity();
        clarificationEntity.setClarificationText("lorem ipsum");
        clarificationEntity.setClarificationId(1);
        questionEntity = new QuestionEntity();
        questionEntity.setQuestionId(1);
        clarificationEntity.setQuestionEntity(questionEntity);
    }

    @Test
    public void ClarificationEntityToDto() {
        ClarificationDto clarificationDto = new ClarificationDto(clarificationEntity);
        assertEquals(clarificationDto.getQuestionId(), clarificationEntity.getQuestionEntity().getQuestionId());
        assertEquals(clarificationDto.getText(), clarificationEntity.getClarificationText());
    }

    @Test
    public void ClarificationDtoToEntity() {
        ClarificationDto clarificationDto = new ClarificationDto();
        clarificationDto.setQuestionId(1);
        clarificationDto.setText("lorem ipsum");
        clarificationDto.setQuestionId(1);

        ClarificationEntity clarificationEntity = new ClarificationEntity(clarificationDto);
        clarificationEntity.setQuestionEntity(questionEntity);

        assertEquals(clarificationEntity.getQuestionEntity().getQuestionId(), clarificationDto.getQuestionId());
        assertEquals(clarificationEntity.getClarificationId(), clarificationDto.getId());
        assertEquals(clarificationEntity.getClarificationText(), clarificationDto.getText());
    }

}