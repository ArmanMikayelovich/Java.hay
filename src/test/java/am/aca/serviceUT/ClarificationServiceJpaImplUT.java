package am.aca.serviceUT;

import am.aca.entity.ClarificationEntity;
import am.aca.repository.ClarificationRepository;
import am.aca.service.ClarificationServiceJpaImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClarificationServiceJpaImplUT {

    @Mock
    private ClarificationRepository clarRepo;

    private ClarificationServiceJpaImpl clarService;

    @Before
    public void init() {
        clarService = new ClarificationServiceJpaImpl(clarRepo);
    }

    @Test(expected = NullPointerException.class)
    public void saveWithNullArgument() {
        clarService.save(null);
    }

    @Test(expected = NullPointerException.class)
    public void saveWithIllegalArgument() {
        clarService.save(new ClarificationEntity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWithIllegalArgument2() {
        ClarificationEntity clarificationEntity = new ClarificationEntity();
        clarificationEntity.setClarificationText("");
        clarService.save(clarificationEntity);
    }

    @Test
    public void saveWithNormalArgument() {
        ClarificationEntity clarificationEntity = new ClarificationEntity();
        clarificationEntity.setClarificationText("text");
        clarService.save(clarificationEntity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findOneByIdWithIllegalArgument() {
        clarService.findOneById(0);
    }

    @Test
    public void findOneByIdWithNormalArgument() {
        clarService.findOneById(1);
        verify(clarRepo, times(1)).findById(any());
    }

    @Test(expected = NullPointerException.class)
    public void changeTextWithNullArguments() {
        clarService.changeText(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void changeTextWithNullArguments2() {
        clarService.changeText(new ClarificationEntity(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeTextWithIllegalArguments() {
        clarService.changeText(new ClarificationEntity(), "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeTextWithIllegalArguments2() {
        ClarificationEntity clarificationEntity = new ClarificationEntity();
        clarificationEntity.setClarificationId(1);
        clarService.changeText(clarificationEntity, "");
    }

    @Test
    public void changeTextWithNormalArguments() {
        ClarificationEntity clarificationEntity = new ClarificationEntity();
        clarificationEntity.setClarificationId(1);
        clarService.changeText(clarificationEntity, "text");
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteByIdWithIllegalArgument() {
        clarService.deleteById(0);
    }

    @Test
    public void deleteById() {
        clarService.deleteById(1);
        verify(clarRepo, times(1)).deleteById(any());
    }

    @Test(expected = NullPointerException.class)
    public void deleteWithNullArgument() {
        clarService.delete(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteWithIllegalArgument() {
        clarService.delete(new ClarificationEntity());
    }

    @Test
    public void delete() {
        ClarificationEntity clarificationEntity = new ClarificationEntity();
        clarificationEntity.setClarificationId(1);

        clarService.delete(clarificationEntity);
    }

    @Test
    public void deleteAll() {
        clarService.deleteAll();
        verify(clarRepo, times(1)).deleteAll();
    }
}