package am.aca.service;

import am.aca.dto.ChapterDto;
import am.aca.entity.ChapterEntity;
import am.aca.entity.ChapterItemEntity;
import am.aca.entity.QuestionEntity;

import java.util.List;
import java.util.Optional;

public interface ChapterService {
    ChapterEntity save(ChapterEntity chapter);

    boolean delete(ChapterEntity chapterEntity);

    ChapterEntity changeName(ChapterEntity chapter, String name);

    Optional<ChapterEntity> findById(Integer id);

    List<ChapterEntity> findAll();

    ChapterEntity addChapterItem(ChapterEntity chapter, ChapterItemEntity item);

    ChapterEntity deleteChapterItem(ChapterEntity chapter, ChapterItemEntity item);

    ChapterEntity deleteAllChapterItems(ChapterEntity chapter);

    ChapterEntity addQuestion(ChapterEntity chapter, QuestionEntity question);

    ChapterEntity deleteQuestion(ChapterEntity chapter, QuestionEntity question);

    ChapterEntity deleteAllQuestions(ChapterEntity chapter);

    ChapterDto toDto(ChapterEntity chapter);

    List<ChapterDto> toDto(List<ChapterEntity> chapterList);

}
