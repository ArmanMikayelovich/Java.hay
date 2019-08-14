package am.aca.entityUT;

import am.aca.dto.ChapterItemDto;
import am.aca.entity.ChapterEntity;
import am.aca.entity.ChapterItemEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChapterItemEntityUT {
    @Test
    public void chapterItemEntityToDtoTest() {
        ChapterItemEntity itemEntity = new ChapterItemEntity();
        itemEntity.setHeadline("lorem ipsum");
        itemEntity.setItemId(1);
        ChapterEntity chapterEntity = new ChapterEntity();
        chapterEntity.setChapterId(1);
        itemEntity.setChapterEntity(chapterEntity);
        ChapterItemDto itemDto = new ChapterItemDto(itemEntity);
        assertEquals(itemDto.getHeadline(), itemEntity.getHeadline());
        assertEquals(itemDto.getId(), itemEntity.getItemId());
        assertEquals(itemDto.getChapterId(), itemEntity.getChapterEntity().getChapterId());
    }

    @Test
    public void chapterDtoToEntityTest() {
        ChapterItemDto itemDto = new ChapterItemDto();
        itemDto.setHeadline("lorem ipsum");
        ChapterItemEntity itemEntity = new ChapterItemEntity(itemDto);
        assertEquals(itemEntity.getHeadline(), itemDto.getHeadline());
    }

    @Test
    public void chapterItemEntityEquality() {
        ChapterItemEntity itemEntity1 = new ChapterItemEntity();
        ChapterItemEntity itemEntity2 = new ChapterItemEntity();
        itemEntity1.setItemId(1);
        itemEntity2.setItemId(1);
        ChapterEntity chapterEntity1 = new ChapterEntity();
        chapterEntity1.getChapterItemList().add(itemEntity1);
        itemEntity1.setChapterEntity(chapterEntity1);
        ChapterEntity chapterEntity2 = new ChapterEntity();
        chapterEntity2.getChapterItemList().add(itemEntity2);
        itemEntity2.setChapterEntity(chapterEntity2);
        itemEntity1.setHeadline("lorem ipsum");
        itemEntity2.setHeadline("lorem ipsum");
        assertEquals(itemEntity1,itemEntity2);
    }
}
