package hay.java.controller.oracleExams;

import hay.java.dto.ChapterDto;
import hay.java.dto.ChapterItemDto;
import hay.java.entity.ChapterEntity;
import hay.java.entity.ChapterItemEntity;
import hay.java.service.ChapterItemService;
import hay.java.service.ChapterService;
import hay.java.service.util.ErrorObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(value = "oracle-exams/chapter-items")
public class ChapterItemControllerSpringMvcImpl implements ChapterItemController {
    private final ChapterService chapterService;
    private final ChapterItemService itemService;

    public ChapterItemControllerSpringMvcImpl(ChapterService chapterService, ChapterItemService itemService) {
        this.chapterService = chapterService;
        this.itemService = itemService;
    }

    /**
     * <b>Path</b> /oracle-exams/chapters-items <br>
     * <b>Method</b> - POST <p>
     * <b>produces</b> JSON
     *
     * @return created chapterItemDto
     */
    @Override
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public Object createItemInChapter(@RequestBody ChapterItemDto item, HttpServletResponse response) {
        if (item.getChapterId() < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("chapterId", "ChapterId must be > 0");
        }
        if (item.getHeadline() == null || item.getHeadline().isEmpty()) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("headline", "headline must be not empty");
        }
        Optional<ChapterEntity> byId = chapterService.findById(item.getChapterId());
        if (byId.isPresent()) {
            ChapterEntity chapterEntity = byId.get();
            ChapterItemEntity itemEntity = new ChapterItemEntity(item);
            chapterService.addChapterItem(chapterEntity, itemEntity);
            return new ChapterItemDto(itemEntity);
        }
        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("chapterId", "Chapter with id " + item.getChapterId() + " not found.");
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
