package am.aca.service;

import am.aca.dto.ChapterDto;
import am.aca.entity.ChapterEntity;
import am.aca.entity.ChapterItemEntity;
import am.aca.entity.QuestionEntity;
import am.aca.repository.ChapterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChapterServiceJpaImpl implements ChapterService {
    private final ChapterRepository chapterRepo;
    private final ChapterItemService itemService;
    private final QuestionService questionService;

    public ChapterServiceJpaImpl(ChapterRepository chapterRepo, ChapterItemService itemService,
                                 QuestionService questionService) {
        this.chapterRepo = chapterRepo;
        this.itemService = itemService;
        this.questionService = questionService;
    }

    @Override
    public ChapterEntity save(ChapterEntity chapter) {
        return chapterRepo.save(chapter);
    }

    @Override
    public boolean delete(ChapterEntity chapterEntity) {
        chapterRepo.delete(chapterEntity);
        return true;
    }

    @Override
    public ChapterEntity changeName(ChapterEntity chapter, String name) {
        chapter.setChapterName(name);
        return chapterRepo.save(chapter);

    }

    @Override
    public Optional<ChapterEntity> findById(Integer id) {
        return chapterRepo.findById(id);
    }

    @Override
    public List<ChapterEntity> findAll() {
        return chapterRepo.findAll();
    }

    @Override
    public ChapterEntity addChapterItem(ChapterEntity chapter, ChapterItemEntity item) {
        chapterRepo.save(chapter);
        chapter.getChapterItemList().add(item);
        item.setChapterEntity(chapter);
        itemService.save(item);
        return chapterRepo.save(chapter);
    }

    @Override
    public ChapterEntity deleteChapterItem(ChapterEntity chapter, ChapterItemEntity item) {
        chapter.getChapterItemList().remove(item);
        itemService.delete(item);
        return chapterRepo.save(chapter);
    }

    @Override
    public ChapterEntity deleteAllChapterItems(ChapterEntity chapter) {
        for (ChapterItemEntity item : chapter.getChapterItemList()) {
            itemService.delete(item);
        }
        chapter.setChapterItemList(new ArrayList<>());
        return chapterRepo.save(chapter);
    }

    @Override
    public ChapterEntity addQuestion(ChapterEntity chapter, QuestionEntity question) {
        chapterRepo.save(chapter);
        chapter.getQuestionEntityList().add(question);
        question.setChapterEntity(chapter);
        questionService.save(question);
        return chapterRepo.save(chapter);
    }

    @Override
    public ChapterEntity deleteQuestion(ChapterEntity chapter, QuestionEntity question) {
        chapter.getQuestionEntityList().remove(question);
        question.setChapterEntity(chapter);
        questionService.save(question);
        return chapterRepo.save(chapter);
    }

    @Override
    public ChapterEntity deleteAllQuestions(ChapterEntity chapter) {
        for (QuestionEntity question : chapter.getQuestionEntityList()) {
            questionService.delete(question);
        }
        chapter.setQuestionEntityList(new ArrayList<>());
        return chapterRepo.save(chapter);
    }

    @Override
    public ChapterDto toDto(ChapterEntity chapter) {
        return new ChapterDto(chapter);
    }

    @Override
    public List<ChapterDto> toDto(List<ChapterEntity> chapterList) {
        return chapterList.stream().map(ChapterDto::new).collect(Collectors.toList());
    }

}
