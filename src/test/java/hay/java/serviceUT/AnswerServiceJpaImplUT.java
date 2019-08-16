package hay.java.serviceUT;

import hay.java.entity.AnswerEntity;
import hay.java.repository.AnswerRepository;
import hay.java.service.AnswerServiceJpaImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AnswerServiceJpaImplUT {

    @Mock
    private AnswerRepository answerRepo;

    private AnswerServiceJpaImpl answerService;

    @Before
    public void init() {
        answerService = new AnswerServiceJpaImpl(answerRepo);
    }

    @Test(expected = NullPointerException.class)
    public void saveWithNull() {
        answerService.save(null);
    }

    @Test
    public void save() {
        answerService.save(new AnswerEntity());
        verify(answerRepo, atLeastOnce()).save(any());
    }


    @Test(expected = IllegalArgumentException.class)
    public void findByIdWithIllegalArgument() {
        answerService.findById(0);
    }

    @Test
    public void findById() {
        answerService.findById(1);
        verify(answerRepo, times(1)).findById(any());
    }

    @Test
    public void findAll() {
        answerService.findAll();
        verify(answerRepo, times(1)).findAll();
    }

    @Test(expected = NullPointerException.class)
    public void deleteWithNullArgument() {
        answerService.delete(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteWithIllegalArgument() {
        answerService.delete(new AnswerEntity());
    }

    @Test
    public void deleteWithNormalArgument() {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setAnswerId(1);
        answerService.delete(answerEntity);
        verify(answerRepo, times(1)).delete(any());
    }

    @Test
    public void deleteAll() {
        answerService.deleteAll();
        verify(answerRepo, times(1)).deleteAll();
    }

    @Test(expected = NullPointerException.class)
    public void changeTextWithNullArguments() {
        answerService.changeText(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void changeTextWithNullTextArguments() {
        answerService.changeText(new AnswerEntity(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeTextWithIllegalArguments() {
        answerService.changeText(new AnswerEntity(), "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeTextWithIllegalArguments2() {
        AnswerEntity answer = new AnswerEntity();
        answer.setAnswerId(1);
        answerService.changeText(answer, "");
    }

    @Test
    public void changeTextWithNormalArguments() {
        AnswerEntity answer = new AnswerEntity();
        answer.setAnswerId(1);
        answerService.changeText(answer, "text");
    }


    @Test(expected = NullPointerException.class)
    public void changeCodeWithNullArguments() {
        answerService.changeCode(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void changeCodeWithNullTextArguments() {
        answerService.changeCode(new AnswerEntity(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeCodeWithIllegalArguments() {
        answerService.changeCode(new AnswerEntity(), "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeCodeWithIllegalArguments2() {
        AnswerEntity answer = new AnswerEntity();
        answer.setAnswerId(1);
        answerService.changeCode(answer, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeCodeWithIllegalArguments3() {
        AnswerEntity answer = new AnswerEntity();
        answer.setAnswerId(1);
        answerService.changeCode(answer, "text");
    }

    @Test
    public void changeCodeWithNormalArguments() {
        AnswerEntity answer = new AnswerEntity();
        answer.setAnswerId(1);
        answerService.changeCode(answer, "T");
    }


    @Test(expected = NullPointerException.class)
    public void changeAccuracyWithNullArgument() {
        answerService.changeAccuracy(null, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeAccuracyWithIllegalArgument() {
        answerService.changeAccuracy(new AnswerEntity(), false);
    }

    @Test
    public void changeAccuracyWithNormalArgument() {
        AnswerEntity answer = new AnswerEntity();
        answer.setAnswerId(1);
        answerService.changeAccuracy(answer, false);
    }
}