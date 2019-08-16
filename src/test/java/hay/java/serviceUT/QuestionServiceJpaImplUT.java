package hay.java.serviceUT;

import hay.java.entity.AnswerEntity;
import hay.java.entity.ClarificationEntity;
import hay.java.entity.QuestionEntity;
import hay.java.repository.QuestionRepository;
import hay.java.service.AnswerService;
import hay.java.service.ClarificationService;
import hay.java.service.QuestionServiceJpaImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceJpaImplUT {
    @Mock
    private QuestionRepository questionRepo;
    @Mock
    private AnswerService answerService;
    @Mock
    private ClarificationService cService;

    private QuestionServiceJpaImpl questionService;

    @Before
    public void init() {
        questionService = new QuestionServiceJpaImpl(questionRepo, answerService, cService);

    }

    @Test(expected = NullPointerException.class)
    public void deleteWithNullArgument() {
        questionService.delete(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteWithIllegalArgument() {
        questionService.delete(new QuestionEntity());
    }

    @Test
    public void deleteWithNormalArgument() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        questionService.delete(question);
        verify(questionRepo, timeout(1)).delete(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findByIdWithIllegalArgument() {
        questionService.findById(0);
    }

    @Test
    public void findById() {
        questionService.findById(1);
        verify(questionRepo, times(1)).findById(any());
    }

    @Test(expected = NullPointerException.class)
    public void saveWithNullArgument() {
        questionService.save(null);
    }

    @Test(expected = NullPointerException.class)
    public void saveWithNullName() {
        questionService.save(new QuestionEntity());
        verify(questionRepo, times(1)).save(any());
    }

    @Test
    public void save() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionText("asd");
        questionService.save(question);
        verify(questionRepo, times(1)).save(any());
    }

    @Test(expected = NullPointerException.class)
    public void changeTextNullArguments() {
        questionService.changeText(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void changeTextNullTextArgument() {
        questionService.changeText(new QuestionEntity(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeTextIllegalArguments() {
        questionService.changeText(new QuestionEntity(), "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeTextIllegalArguments2() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        questionService.changeText(question, "");
    }

    @Test
    public void changeTextNormalArguments() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        questionService.changeText(question, "text");
    }


    @Test(expected = NullPointerException.class)
    public void changeCodeWithNullArguments() {
        questionService.changeCode(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void changeCodeWithNullCodeArgument() {
        questionService.changeCode(new QuestionEntity(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeCodeWithIllegalArgument() {
        questionService.changeCode(new QuestionEntity(), "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeCodeWithIllegalArgument2() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        questionService.changeCode(question, "");
    }

    @Test
    public void changeCodeWithNormalArguments() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        questionService.changeCode(question, "text");
        verify(questionRepo, times(1)).save(any());
    }

    @Test(expected = NullPointerException.class)
    public void addAnswerWithNullArguments() {
        questionService.addAnswer(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void addAnswerWithNullAnswerArgument() {
        questionService.addAnswer(new QuestionEntity(), null);
    }

    @Test(expected = NullPointerException.class)
    public void addAnswerWithIllegalArguments() {
        questionService.addAnswer(new QuestionEntity(), new AnswerEntity());
    }

    @Test(expected = NullPointerException.class)
    public void addAnswerWithIllegalArguments2() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        questionService.addAnswer(question, new AnswerEntity());
    }

    @Test(expected = NullPointerException.class)
    public void addAnswerWithIllegalArguments3() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        AnswerEntity answer = new AnswerEntity();
        answer.setAnswerCode("");
        questionService.addAnswer(question, answer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAnswerWithIllegalArguments4() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        AnswerEntity answer = new AnswerEntity();
        answer.setAnswerCode("");
        answer.setAnswerText("");
        questionService.addAnswer(question, answer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAnswerWithIllegalArguments5() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        AnswerEntity answer = new AnswerEntity();
        answer.setAnswerCode("A");
        answer.setAnswerText("");
        questionService.addAnswer(question, answer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAnswerWithIllegalArguments6() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        AnswerEntity answer = new AnswerEntity();
        answer.setAnswerCode("AA");
        answer.setAnswerText("text");
        questionService.addAnswer(question, answer);
    }

    @Test
    public void addAnswerWithNormalArguments() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        AnswerEntity answer = new AnswerEntity();
        answer.setAnswerCode("B");
        answer.setAnswerText("text");
        questionService.addAnswer(question, answer);
        verify(answerService, times(1)).save(any());
        verify(questionRepo, atLeastOnce()).save(any());
    }


    @Test(expected = NullPointerException.class)
    public void deleteAnswerWithNullArguments() {
        questionService.deleteAnswer(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteAnswerWithNullAnswerArgument() {
        questionService.deleteAnswer(new QuestionEntity(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteAnswerWithIllegalArguments() {
        questionService.deleteAnswer(new QuestionEntity(), new AnswerEntity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteAnswerWithIllegalArguments2() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        questionService.deleteAnswer(question, new AnswerEntity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteAnswerWithIllegalArguments3() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        AnswerEntity answer = new AnswerEntity();
        answer.setAnswerId(1);
        questionService.deleteAnswer(question, answer);
    }

    @Test
    public void deleteAnswerWithNormalArguments() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        AnswerEntity answer = new AnswerEntity();
        answer.setAnswerId(1);
        answer.setQuestionEntity(question);
        questionService.deleteAnswer(question, answer);
        verify(questionRepo, times(1)).save(any());
        verify(answerService, times(1)).delete(any());
    }

    @Test(expected = NullPointerException.class)
    public void deleteAllAnswerWithNullArguments() {
        questionService.deleteAllAnswer(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteAllAnswerWithIllegalArguments() {
        questionService.deleteAllAnswer(new QuestionEntity());
    }

    @Test
    public void deleteAllAnswerWithNormalArguments() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        questionService.deleteAllAnswer(question);
    }

    @Test(expected = NullPointerException.class)
    public void changeClarificationWithNullArguments() {
        questionService.changeClarification(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void changeClarificationWithIllegalArguments() {
        questionService.changeClarification(new QuestionEntity(), null);
    }

    @Test(expected = NullPointerException.class)
    public void changeClarificationWithIllegalArguments2() {
        questionService.changeClarification(new QuestionEntity(), new ClarificationEntity());
    }

    @Test(expected = NullPointerException.class)
    public void changeClarificationWithIllegalArguments3() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        questionService.changeClarification(question, new ClarificationEntity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeClarificationWithIllegalArguments4() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        ClarificationEntity clarification = new ClarificationEntity();
        clarification.setClarificationText("");
        questionService.changeClarification(question, clarification);
    }

    @Test
    public void changeClarificationWithNormalArguments() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionId(1);
        ClarificationEntity clarification = new ClarificationEntity();
        clarification.setClarificationText("text");
        questionService.changeClarification(question, clarification);
    }


}