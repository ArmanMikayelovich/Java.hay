package am.aca.controller.oracleExams;

import am.aca.dto.ChapterDto;
import am.aca.dto.ChapterItemDto;

import javax.servlet.http.HttpServletResponse;

public interface ChapterItemController {
     Object createItemInChapter(ChapterItemDto item, HttpServletResponse response);

    Object changeItem(ChapterItemDto item, HttpServletResponse response);

    Object deleteItem(ChapterItemDto item, HttpServletResponse response);

    Object deleteAllItemsInChapter(ChapterDto chapter, HttpServletResponse response);

    Object getAllItemsInChapter(ChapterDto chapter, HttpServletResponse response);

    Object getItemById(Integer id);





}
