package hay.java.serviceUT;

import hay.java.entity.ChapterItemEntity;
import hay.java.repository.ChapterItemRepository;
import hay.java.service.ChapterItemsServiceJpaImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ChapterItemsServiceJpaImplUT {

    @Mock
    private ChapterItemRepository itemRepo;

    private ChapterItemsServiceJpaImpl itemService;

    @Before
    public void init() {
        itemService = new ChapterItemsServiceJpaImpl(itemRepo);
    }

    @Test(expected = NullPointerException.class)
    public void saveWithNullArgument() {
        itemService.save(null);
    }

    @Test
    public void save() {
        itemService.save(new ChapterItemEntity());
        verify(itemRepo, times(1)).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findOneByIdWithIllegalArgument() {
        itemService.findOneById(0);
    }

    @Test
    public void findOneById() {
        itemService.findOneById(1);
        verify(itemRepo, times(1)).findById(any());
    }

    @Test
    public void findAll() {
        itemService.findAll();
        verify(itemRepo, times(1)).findAll();
    }

    @Test(expected = NullPointerException.class)
    public void changeHeadlineWithNullArguments() {
        itemService.changeHeadline(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void changeHeadlineWithNullArguments2() {
        itemService.changeHeadline(new ChapterItemEntity(), null);
    }

    @Test(expected = NullPointerException.class)
    public void changeHeadlineWithNullArgument3() {
        ChapterItemEntity item = new ChapterItemEntity();
        item.setItemId(1);
        itemService.changeHeadline(item, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeHeadlineWithIllegalArguments() {
        ChapterItemEntity item = new ChapterItemEntity();
        item.setItemId(1);
        itemService.changeHeadline(item, "");
    }

    @Test
    public void changeHeadlineWithNormalArguments() {
        ChapterItemEntity item = new ChapterItemEntity();
        item.setItemId(1);
        itemService.changeHeadline(item, "lorem ipsum");
    }

    @Test(expected = NullPointerException.class)
    public void deleteWithNullArgument() {
        itemService.delete(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void deleteWithIllegalArgument() {
        itemService.delete(new ChapterItemEntity());
    }

    @Test
    public void deleteWithNormalArgument() {
        ChapterItemEntity itemEntity = new ChapterItemEntity();
        itemEntity.setItemId(1);
        itemService.delete(itemEntity);
    }
}