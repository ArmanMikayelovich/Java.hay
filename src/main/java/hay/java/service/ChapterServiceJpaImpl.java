package hay.java.service;

import hay.java.dto.ChapterDto;
import hay.java.entity.ChapterEntity;
import hay.java.entity.ChapterItemEntity;
import hay.java.entity.QuestionEntity;
import hay.java.repository.ChapterRepository;
import hay.java.service.interfaces.ChapterItemService;
import hay.java.service.interfaces.ChapterService;
import hay.java.service.interfaces.QuestionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    /**
     * Saving chapter to DB.
     *
     * @param chapter must be <b>NonNull</b> with not null and not empty name
     * @return return saved entity with inputed id.
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    @Override
    @Transactional
    public ChapterEntity save(ChapterDtoՆ) {
        log.debug("saving {}" + chapter);
        if (Objects.requireNonNull(chapter).getChapterName().isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(Objects.toString(chapterRepo));
            log.error(exception);
            throw exception;
        }
        ChapterEntity save = chapterRepo.save(chapter);
        log.debug(save + " saved");
        return save;
    }

    /**
     * Delete chapter
     *
     * @param chapterEntity must be nonnull
     * @return true when successfully deleted.
     * @throws NullPointerException
     */
    @Override
    @Transactional
    public boolean delete(ChapterEntity chapterEntity) {
        log.debug("deleting" + chapterEntity);
        if (chapterEntity == null) {
            log.error("chapter is null");
            throw new NullPointerException();
        }
        chapterRepo.delete(chapterEntity);
        log.debug("deleted chapter with id " + chapterEntity.getChapterId());
        return true;
    }

    /**
     * Update name of chapter
     *
     * @param chapter <b>NonNull</b> with legal id (id > 0)
     * @param name    <b>NonNull</b> not empty String
     * @return renamed chapter
     * @throws IllegalArgumentException when chapters id < 1 or name is empty
     * @throws NullPointerException
     */
    @Override
    @Transactional
    public ChapterEntity changeName(ChapterEntity chapter, String name) {
        log.debug("Changing name of " + chapter + "to " + name);
        if (Objects.requireNonNull(chapter).getChapterId() < 1 || Objects.requireNonNull(name).isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(Objects.toString(chapter) +
                    " " + Objects.requireNonNull(name));
            log.error(exception);
            throw exception;
        }
        chapter.setChapterName(name);

        ChapterEntity save = chapterRepo.save(chapter);
        log.debug("Name of chapter" + save + "changed");
        return save;
    }

    /**
     * Find chapter by id
     *
     * @param id must be > 0
     * @return Optional<ChapterEntity>
     * @throws IllegalArgumentException
     */
    @Override
    @Transactional
    public Optional<ChapterEntity> findById(Integer id) {
        if (id < 1) {
            IllegalArgumentException exception = new IllegalArgumentException("id must be > 0 || id = " + id);
            log.error(exception);
            throw exception;
        }
        log.debug("searching Chapter by id " + id);
        Optional<ChapterEntity> byId = chapterRepo.findById(id);
        if (byId.isPresent()) {
            log.debug("Chapter found " + byId.get());
        } else {
            log.debug("chapter with id " + id + "not found.");
        }
        return byId;
    }

    /**
     * Find all Chapters in DB.
     *
     * @return All chapterEntitiyes from DB.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ChapterEntity> findAll() {
        log.debug("requested all chapters.WHY?:)");
        return chapterRepo.findAll();
    }

    /**
     * Add ChapterItem in chapter
     *
     * @param chapter <b>NonNull</b> ChapterEntity with legal id( id > 0)
     * @param item    <b>NonNull</b> ChapterItemEntity with <b>NonNull</b> <b>Not Empty</b> headline.
     * @return saved ChapterEntity from DB.
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    @Override
    @Transactional
    public ChapterEntity addChapterItem(ChapterEntity chapter, ChapterItemEntity item) {
        log.debug("Adding " + item + " to " + chapter);
        if (Objects.requireNonNull(chapter).getChapterId() < 1 | Objects.requireNonNull(item).getHeadline().isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(Objects.toString(chapter + " " + Objects.toString(item)));
            log.error(exception);
            throw exception;
        }
        chapterRepo.save(chapter);
        chapter.getChapterItemList().add(item);
        item.setChapterEntity(chapter);
        itemService.save(item);
        ChapterEntity save = chapterRepo.save(chapter);
        log.debug("ChapterItem " + item + "added to " + chapter);
        return save;
    }

    /**
     * Delete item from chapter
     *
     * @param chapter <b>NonNull</b> ChapterEntity with legal id (id > 0)
     * @param item    <b>NonNull</b> ChapterItemEntity with <b>NonNull</b> <b>Not empty</b> headline
     * @return chapterEntity, with deleted item
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    @Override
    @Transactional
    public ChapterEntity deleteChapterItem(ChapterEntity chapter, ChapterItemEntity item) {
        log.debug("Deleting " + item + "from " + chapter);
        if (Objects.requireNonNull(chapter).getChapterId() < 1 | Objects.requireNonNull(item).getHeadline().isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(chapter + " " + item);
            log.error(exception);
            throw exception;
        }
        chapter.getChapterItemList().remove(item);
        itemService.delete(item);
        ChapterEntity save = chapterRepo.save(chapter);
        log.debug("deleted" + item + "from " + save);
        return save;
    }

    /**
     * Delete all ChapterItems from chapter
     * @param chapter <b>NonNull</b> withj legal id ( id > 0)
     * @return ChapterEntity with 0 items
     */
    @Override
    @Transactional
    public ChapterEntity deleteAllChapterItems(ChapterEntity chapter) {

        log.debug("deleting all item from " + chapter);
        if (Objects.requireNonNull(chapter).getChapterId() < 1) {
            IllegalArgumentException exception = new IllegalArgumentException("id must be > 0 || id = " + chapter.getChapterId());
            log.error(exception);
            throw exception;
        }
        for (ChapterItemEntity item : chapter.getChapterItemList()) {
            itemService.delete(item);
        }
        chapter.setChapterItemList(new ArrayList<>());
        ChapterEntity save = chapterRepo.save(chapter);
        log.debug("All item from " + save + " deleted.");
        return save;
    }

    /**
     *Adding question to chapter
     * @param chapter <b>NonNull</b> ChapterEntity with legal id ( id . 0)
     * @param question <b>NonNull</b> QuestionEntity with <b>NonNull</b> <b>not empty</b> text
     * @return saved ChapterEntity.
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    @Override
    @Transactional
    public ChapterEntity addQuestion(ChapterEntity chapter, QuestionEntity question) {
        log.debug("adding " + question + " to " + chapter);
        if (Objects.requireNonNull(chapter).getChapterId() < 1 | Objects.requireNonNull(question).getQuestionText().isEmpty()) {
            IllegalArgumentException exception = new IllegalArgumentException(chapter + " " + question);
            log.error(exception);
            throw exception;
        }
        chapterRepo.save(chapter);
        chapter.getQuestionEntityList().add(question);
        question.setChapterEntity(chapter);
        questionService.save(question);
        ChapterEntity save = chapterRepo.save(chapter);
        log.debug(question + " added to " + chapter);
        return save;
    }

    /**
     * Delete Question from chapter.
     * <b>Mandatory</b> <code>chapter.getChapterId() == QuestionEntity.getChapterEntity.getChapterId()</code>
     * @param chapter <b>NonNull</b> ChapterEntity with legal id (id > 0)
     * @param question <b>NonNull</b> QuestionEntity with legal id (id > 0)
     * @return saved ChapterEntity.
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    @Override
    @Transactional
    public ChapterEntity deleteQuestion(ChapterEntity chapter, QuestionEntity question) {
        log.debug("removing " + question + "from " + chapter);
        if (Objects.requireNonNull(chapter).getChapterId() < 1 | Objects.requireNonNull(question).getId() < 0
                | question.getChapterEntity().getChapterId() != chapter.getChapterId()) {
            IllegalArgumentException exception = new IllegalArgumentException(chapter + " " + question);
            log.error(exception);
            throw exception;
        }
        chapter.getQuestionEntityList().remove(question);
        question.setChapterEntity(chapter);
        questionService.save(question);
        ChapterEntity save = chapterRepo.save(chapter);
        log.debug("Question " + question + " removed from " + chapter);
        return save;
    }

    /**
     * Delete all qeustions from chapter
     * @param chapter <b>NonNull</b> ChapterEntity with legal id ( id > 0)
     * @return saved ChapterEntity from DB.
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    @Override
    @Transactional
    public ChapterEntity deleteAllQuestions(ChapterEntity chapter) {
        log.debug("deleting all items from " + chapter);
        if (Objects.requireNonNull(chapter).getChapterId() < 1) {
            IllegalArgumentException exception = new IllegalArgumentException("id must be > 0 " + chapter);
            log.error(exception);
            throw exception;
        }
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
