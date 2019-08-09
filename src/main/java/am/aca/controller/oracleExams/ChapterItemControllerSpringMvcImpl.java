package am.aca.controller.oracleExams;

import am.aca.dto.ChapterDto;
import am.aca.dto.ChapterItemDto;
import am.aca.entity.ChapterEntity;
import am.aca.entity.ChapterItemEntity;
import am.aca.service.ChapterItemService;
import am.aca.service.ChapterService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "oracle-exams/chapter-items")
public class ChapterItemControllerSpringMvcImpl implements ChapterItemController {
    private final ChapterService chapterService;
    private final ChapterItemService itemService;

    public ChapterItemControllerSpringMvcImpl(ChapterService chapterService, ChapterItemService itemService) {
        this.chapterService = chapterService;
        this.itemService = itemService;
    }

    @Override
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public Object createItemInChapter(@RequestBody ChapterItemDto item, HttpServletResponse response) {
        ChapterEntity chapterEntity = chapterService.findById(item.getChapterId()).get();
        ChapterItemEntity itemEntity = new ChapterItemEntity(item);
        chapterService.addChapterItem(chapterEntity, itemEntity);
        return new ChapterItemDto(itemEntity);
    }

    @Override
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json")
    public Object changeItem(@RequestBody ChapterItemDto item, HttpServletResponse response) {
        ChapterItemEntity itemEntity = itemService.findOneById(item.getId()).get();
        itemEntity.setHeadline(item.getHeadline());
        itemService.save(itemEntity);
        return new ChapterItemDto(itemEntity);
    }

    @Override
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Object deleteItem(@RequestBody ChapterItemDto item, HttpServletResponse response) {
        ChapterItemEntity itemEntity = itemService.findOneById(item.getId()).get();
        chapterService.deleteChapterItem(itemEntity.getChapterEntity(), itemEntity);
        return null;
    }

    @Override
    @RequestMapping(value = "/delete-all", method = RequestMethod.DELETE)
    public Object deleteAllItemsInChapter(@RequestBody ChapterDto chapter, HttpServletResponse response) {
        ChapterEntity chapterEntity = chapterService.findById(chapter.getChapterId()).get();
        chapterService.deleteAllChapterItems(chapterEntity);
        return null;
    }

    @Override
    @RequestMapping(value = "/in-chapter", method = RequestMethod.GET, produces = "application/json")
    public Object getAllItemsInChapter(@RequestBody ChapterDto chapter, HttpServletResponse response) {
        ChapterEntity chapterEntity = chapterService.findById(chapter.getChapterId()).get();
        return itemService.toDto(chapterEntity.getChapterItemList());
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getItemById(@PathVariable("id") Integer id) {
        ChapterItemEntity chapterEntity = itemService.findOneById(id).get();
        return new ChapterItemDto(chapterEntity);
    }


}
