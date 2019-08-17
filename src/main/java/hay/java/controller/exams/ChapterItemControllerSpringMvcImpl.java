package hay.java.controller.exams;

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

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "exams/chapter-items")
public class ChapterItemControllerSpringMvcImpl implements ChapterItemController {
    private final ChapterService chapterService;
    private final ChapterItemService itemService;

    public ChapterItemControllerSpringMvcImpl(ChapterService chapterService, ChapterItemService itemService) {
        this.chapterService = chapterService;
        this.itemService = itemService;
    }

    /**
     * <b>Path</b> /exams/chapters-items <br>
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
            return new ErrorObject("headline", "headline must be not empty.");
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

    /**
     * <b>Path</b> /exams/update <br>
     * <b>Method</b> - PUT <p>
     * <b>produces</b> JSON
     *
     * @return updated chapter item
     */
    @Override
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json")
    public Object changeItem(@RequestBody ChapterItemDto item, HttpServletResponse response) {
        if (item.getId() < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("id", "id must be > 0");
        }
        Optional<ChapterItemEntity> byId = itemService.findOneById(item.getId());
        if (byId.isPresent()) {
            ChapterItemEntity itemEntity = byId.get();
            if (item.getHeadline().equals(itemEntity.getHeadline())) {
                response.setStatus(BAD_REQUEST.value());
                return new ErrorObject("headline", "headline equals to headline in database.");
            }
            itemEntity.setHeadline(item.getHeadline());
            itemService.save(itemEntity);
            return item.set(itemEntity);
        }

        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("id", "item with id " + item.getId() + " not found.");
    }

    @Override
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Object deleteItem(@RequestBody ChapterItemDto item, HttpServletResponse response) {
        if (item.getId() < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("id", "id must be > 0");
        }
        Optional<ChapterItemEntity> byId = itemService.findOneById(item.getId());
        if (byId.isPresent()) {
            ChapterItemEntity itemEntity = byId.get();
            chapterService.deleteChapterItem(itemEntity.getChapterEntity(), itemEntity);
            return "item successfully deleted";
        }
        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("id", "item with id " + item.getId() + " not found.");
    }

    @Override
    @RequestMapping(value = "/delete-all", method = RequestMethod.DELETE)
    public Object deleteAllItemsInChapter(@RequestBody ChapterDto chapter, HttpServletResponse response) {
        if (chapter.getChapterId() < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("chapterId", "chapterId must be > 0");
        }
        Optional<ChapterEntity> byId = chapterService.findById(chapter.getChapterId());
        if (byId.isPresent()) {
            ChapterEntity chapterEntity = byId.get();
            chapterService.deleteAllChapterItems(chapterEntity);
            return "All items in chapter " + chapter.getChapterId() + " has been successfully deleted";
        }
        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("chapterId", "chapter with id " + chapter.getChapterId() + " not found.");
    }

    @Override
    @RequestMapping(value = "/in-chapter/{chapterId}", method = RequestMethod.GET, produces = "application/json")
    public Object getAllItemsInChapter(@PathVariable("chapterId") int chapterId, HttpServletResponse response) {
        if (chapterId < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("chapterId", "chapterId must be > 0");
        }
        Optional<ChapterEntity> byId = chapterService.findById(chapterId);
        if (byId.isPresent()) {
            ChapterEntity chapterEntity = byId.get();
            return itemService.toDto(chapterEntity.getChapterItemList());
        }
        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("chapterId", "Chapter with id " + chapterId + " not found.");
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getItemById(@PathVariable("id") int id,HttpServletResponse response) {
        if (id < 1) {
            response.setStatus(BAD_REQUEST.value());
            return new ErrorObject("id", "id must be > 0");
        }
        Optional<ChapterItemEntity> byId = itemService.findOneById(id);
        if (byId.isPresent()) {
            ChapterItemEntity chapterEntity = byId.get();
            return new ChapterItemDto(chapterEntity);
        }
        response.setStatus(NOT_FOUND.value());
        return new ErrorObject("id", "ChapterItem with id " + id + " not found.");
    }


}
