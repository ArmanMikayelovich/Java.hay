package hay.java.controller.exams;

import hay.java.dto.ChapterDto;
import hay.java.dto.ChapterItemDto;

import javax.servlet.http.HttpServletResponse;

public interface ChapterItemController {
    Object createItemInChapter(ChapterItemDto item, HttpServletResponse response);

    Object changeItem(ChapterItemDto item, HttpServletResponse response);

    Object deleteItem(ChapterItemDto item, HttpServletResponse response);

    Object deleteAllItemsInChapter(ChapterDto chapter, HttpServletResponse response);

    Object getAllItemsInChapter(int chapterId, HttpServletResponse response);

    Object getItemById(int id, HttpServletResponse response);


}
