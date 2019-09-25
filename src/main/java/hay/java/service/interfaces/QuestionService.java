package hay.java.service.interfaces;

import hay.java.dto.QuestionDto;
import hay.java.entity.AnswerEntity;
import hay.java.entity.ClarificationEntity;
import hay.java.entity.QuestionEntity;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    boolean delete(QuestionEntity question);

    Optional<QuestionEntity> findById(int id);

    QuestionEntity save(QuestionEntity question);

    QuestionEntity changeText(QuestionEntity question, String text);

    QuestionEntity changeCode(QuestionEntity question, String code);

    QuestionEntity addAnswer(QuestionEntity question, AnswerEntity answer);

    QuestionEntity deleteAnswer(QuestionEntity question, AnswerEntity answer);

    QuestionEntity deleteAllAnswer(QuestionEntity question);

    /**
     * delete <b>old</b> clarification, if exists, then adding newest and save them
     * @param question our current question.
     * @param clarification new clarification to save.
     * @return
     */
    QuestionEntity changeClarification(QuestionEntity question, ClarificationEntity clarification);

    List<QuestionDto> toDto(List<QuestionEntity> questionEntityList);

    QuestionDto toDto(QuestionEntity questionEntity);



}
