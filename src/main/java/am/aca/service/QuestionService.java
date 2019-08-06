package am.aca.service;

import am.aca.entity.AnswerEntity;
import am.aca.entity.ClarificationEntity;
import am.aca.entity.QuestionEntity;

public interface QuestionService {
    boolean delete(QuestionEntity question);

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





}
