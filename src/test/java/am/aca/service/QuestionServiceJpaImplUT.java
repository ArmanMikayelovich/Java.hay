package am.aca.service;

import am.aca.entity.QuestionEntity;
import am.aca.repository.QuestionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.hamcrest.CoreMatchers.*;

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
    }  @Test
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
        verify(questionRepo,times(1)).save(any());
    }  @Test
    public void save() {
        QuestionEntity question = new QuestionEntity();
        question.setQuestionText("asd");
        questionService.save(question);
        verify(questionRepo,times(1)).save(any());
    }

    @Test
    public void changeText() {
    }

    @Test
    public void changeCode() {
    }

    @Test
    public void addAnswer() {
    }

    @Test
    public void deleteAnswer() {
    }

    @Test
    public void deleteAllAnswer() {
    }

    @Test
    public void changeClarification() {
    }

    @Test
    public void toDto() {
    }
}