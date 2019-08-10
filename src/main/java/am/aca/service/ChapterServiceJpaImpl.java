package am.aca.service;

import am.aca.dto.ChapterDto;
import am.aca.entity.ChapterEntity;
import am.aca.entity.ChapterItemEntity;
import am.aca.entity.QuestionEntity;
import am.aca.repository.ChapterRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChapterServiceJpaImpl implements ChapterService {
    private static final Logger log = LogManager.getLogger(ChapterServiceJpaImpl.class);
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
        log.debug("saving " + chapter);
        ChapterEntity save = chapterRepo.save(chapter);
        log.debug(save + " saved");
        return save;
    }

    @Override
    public boolean delete(ChapterEntity chapterEntity) {
        log.debug("deleting" + chapterEntity);
        chapterRepo.delete(chapterEntity);
        log.debug("deleted chapter with id " + chapterEntity.getChapterId());
        return true;
    }

    @Override
    public ChapterEntity changeName(ChapterEntity chapter, String name) {
        log.debug("Changing name of " + chapter + "to " + name);
        chapter.setChapterName(name);

        ChapterEntity save = chapterRepo.save(chapter);
        log.debug("Name of chapter" + save + "changed");
        return save;
    }

    @Override
    public Optional<ChapterEntity> findById(Integer id) {
        log.debug("searching Chapter by id " + id);
        Optional<ChapterEntity> byId = chapterRepo.findById(id);
        if (byId.isPresent()) {
            log.debug("Chapter found " + byId.get());
        } else {
            log.debug("chapter with id " + id + "not found.");
        }
        return byId;
    }

    @Override
    public List<ChapterEntity> findAll() {
        log.debug("requested all chapters.WHY?:)");
        return chapterRepo.findAll();
    }

    @Override
    public ChapterEntity addChapterItem(ChapterEntity chapter, ChapterItemEntity item) {
        log.debug("Adding " + item + "to " + chapter);
        chapterRepo.save(chapter);
        chapter.getChapterItemList().add(item);
        item.setChapterEntity(chapter);
        itemService.save(item);
        ChapterEntity save = chapterRepo.save(chapter);
        log.debug("ChapterItem " + item + "added to " + chapter);
        return save;
    }

    @Override
    public ChapterEntity deleteChapterItem(ChapterEntity chapter, ChapterItemEntity item) {
        log.debug("Deleting " + item + "from " + chapter);
        chapter.getChapterItemList().remove(item);
        itemService.delete(item);
        ChapterEntity save = chapterRepo.save(chapter);
        log.debug("deleted" + item + "from " + save);
        return save;
    }

    @Override
    public ChapterEntity deleteAllChapterItems(ChapterEntity chapter) {
        log.debug("deleting all item from " + chapter);
        for (ChapterItemEntity item : chapter.getChapterItemList()) {
            itemService.delete(item);
        }
        chapter.setChapterItemList(new ArrayList<>());
        ChapterEntity save = chapterRepo.save(chapter);
        log.debug("All item from " + save + " deleted.");
        return save;
    }

    @Override
    public ChapterEntity addQuestion(ChapterEntity chapter, QuestionEntity question) {
        log.debug("adding " + question + " to " + chapter);
        chapterRepo.save(chapter);
        chapter.getQuestionEntityList().add(question);
        question.setChapterEntity(chapter);
        questionService.save(question);
        ChapterEntity save = chapterRepo.save(chapter);
        log.debug(question + " added to " + chapter);
        return save;
    }

    @Override
    public ChapterEntity deleteQuestion(ChapterEntity chapter, QuestionEntity question) {
        log.debug("removing " + question + "from " + chapter);
        chapter.getQuestionEntityList().remove(question);
        question.setChapterEntity(chapter);
        questionService.save(question);
        ChapterEntity save = chapterRepo.save(chapter);
        log.debug("Question " + question + " removed from " + chapter);
        return save;
    }

    @Override
    public ChapterEntity deleteAllQuestions(ChapterEntity chapter) {
        log.debug("deleting all items from " + chapter);
        for (QuestionEntity question : chapter.getQuestionEntityList()) {
            questionService.delete(question);
        }
        chapter.setQuestionEntityList(new ArrayList<>());
        ChapterEntity save = chapterRepo.save(chapter);
        log.debug("All questions deleted from " + save);
        return save;
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
