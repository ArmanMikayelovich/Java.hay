package am.aca.serviceUT;

import am.aca.entity.ChapterEntity;
import am.aca.entity.ChapterItemEntity;
import am.aca.entity.QuestionEntity;
import am.aca.repository.ChapterRepository;
import am.aca.service.ChapterItemService;
import am.aca.service.ChapterServiceJpaImpl;
import am.aca.service.QuestionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ChapterServiceJpaImplUT {
    @Mock
    private QuestionService questionService;
    @Mock
    private ChapterItemService itemService;
    @Mock
    private ChapterRepository chapterRepo;

    private ChapterServiceJpaImpl chapterService;

    @Before
    public void init() {
        chapterService = new ChapterServiceJpaImpl(chapterRepo, itemService, questionService);
    }

    @Test(expected = NullPointerException.class)
    public void saveNullArgument() {
        chapterService.save(null);
    }

    @Test(expected = NullPointerException.class)
    public void saveWithNullChapterName() {
        chapterService.save(new ChapterEntity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWithIllegalArgument() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterName("");
        chapterService.save(chapter);
    }

    @Test
    public void save() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterName("lorem ipsum");
        chapterService.save(chapter);
        verify(chapterRepo, times(1)).save(any());
    }

    @Test(expected = NullPointerException.class)
    public void deleteNull() {
        chapterService.delete(null);
    }

    @Test(expected = NullPointerException.class)
    public void delete() {
        chapterService.delete(null);
        verify(chapterRepo, times(1)).delete(any());
    }

    @Test(expected = NullPointerException.class)
    public void changeNameNull() {
        chapterService.changeName(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void changeNameNullNameArgument() {
        chapterService.changeName(new ChapterEntity(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeNameIllegalArguments() {
        chapterService.changeName(new ChapterEntity(), "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeNameIllegalArguments2() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        chapterService.changeName(chapter, "");
    }

    @Test
    public void changeName() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        chapterService.changeName(chapter, "text");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findByIdWithZero() {
        chapterService.findById(0);
    }

    @Test
    public void findByIdNormalArgument() {
        chapterService.findById(1);
    }

    @Test
    public void findAll() {
        chapterService.findAll();
        verify(chapterRepo, times(1)).findAll();
    }

    @Test(expected = NullPointerException.class)
    public void addChapterItemWithNullArguments() {
        chapterService.addChapterItem(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void addChapterItemWithNullItemArgument() {
        chapterService.addChapterItem(new ChapterEntity(), null);
    }

    @Test(expected = NullPointerException.class)
    public void addChapterItemWithIllegalArguments() {
        chapterService.addChapterItem(new ChapterEntity(), new ChapterItemEntity());
    }

    @Test(expected = NullPointerException.class)
    public void addChapterItemWithIllegalItemArgument() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        chapterService.addChapterItem(chapter, new ChapterItemEntity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addChapterItemWithIllegalItemArgument2() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        ChapterItemEntity item = new ChapterItemEntity();
        item.setHeadline("");
        chapterService.addChapterItem(chapter, item);
    }

    @Test
    public void addChapterItemWithNormalArguments() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        ChapterItemEntity item = new ChapterItemEntity();
        item.setHeadline("text");
        chapterService.addChapterItem(chapter, item);
    }


    @Test(expected = NullPointerException.class)
    public void deleteChapterItemWithNullArguments() {
        chapterService.deleteChapterItem(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteChapterItemWithNullItemArgument() {
        chapterService.deleteChapterItem(new ChapterEntity(), null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteChapterItemWithIllegalArguments() {
        chapterService.deleteChapterItem(new ChapterEntity(), new ChapterItemEntity());
    }

    @Test(expected = NullPointerException.class)
    public void deleteChapterItemWithIllegalArguments2() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        chapterService.deleteChapterItem(chapter, new ChapterItemEntity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteChapterItemWithIllegalArguments3() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        ChapterItemEntity item = new ChapterItemEntity();
        item.setHeadline("");
        chapterService.deleteChapterItem(chapter, item);
    }

    @Test
    public void deleteChapterItemWithNormalArguments() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        ChapterItemEntity item = new ChapterItemEntity();
        item.setHeadline("text");
        chapterService.deleteChapterItem(chapter, item);
    }


    @Test(expected = NullPointerException.class)
    public void deleteAllChapterItemsWithNullArgument() {
        chapterService.deleteAllChapterItems(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteAllWithIllegalArgument() {
        chapterService.deleteAllChapterItems(new ChapterEntity());
    }

    @Test
    public void deleteAll() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        chapterService.deleteAllChapterItems(chapter);
        verify(chapterRepo, times(1)).save(any());
    }

    @Test(expected = NullPointerException.class)
    public void addQuestionWithNullArguments() {
        chapterService.addQuestion(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void addQuestionWithQuestionNullArguments() {
        chapterService.addQuestion(new ChapterEntity(), null);
    }

    @Test(expected = NullPointerException.class)
    public void addQuestionWithIllegalArgumentsArguments() {
        chapterService.addQuestion(new ChapterEntity(), new QuestionEntity());
    }

    @Test(expected = NullPointerException.class)
    public void addQuestionWithIllegalArgumentsArguments2() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        chapterService.addQuestion(chapter, new QuestionEntity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addQuestionWithIllegalArgumentsArguments3() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        QuestionEntity question = new QuestionEntity();
        question.setQuestionText("");
        chapterService.addQuestion(chapter, question);
    }

    @Test
    public void addQuestionWithNormalArguments() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        QuestionEntity question = new QuestionEntity();
        question.setQuestionText("text");
        chapterService.addQuestion(chapter, question);
        verify(chapterRepo, atLeastOnce()).save(any());
    }

    @Test(expected = NullPointerException.class)
    public void deleteQuestionWithNullArguments() {
        chapterService.deleteQuestion(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteQuestionWithNullQuestionArgument() {
        chapterService.deleteQuestion(new ChapterEntity(), null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteQuestionWithIllegalArguments() {
        chapterService.deleteQuestion(new ChapterEntity(), new QuestionEntity());
    }

    @Test(expected = NullPointerException.class)
    public void deleteQuestionWithIllegalArguments2() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        chapterService.deleteQuestion(chapter, new QuestionEntity());
    }

    @Test(expected = NullPointerException.class)
    public void deleteQuestionWithIllegalArguments3() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        QuestionEntity question = new QuestionEntity();
        question.setQuestionText("");
        chapterService.deleteQuestion(chapter, question);
    }

    @Test(expected = NullPointerException.class)
    public void deleteQuestionWithIllegalArguments4() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        QuestionEntity question = new QuestionEntity();
        question.setQuestionText("text");
        chapterService.deleteQuestion(chapter, question);
    }

    @Test
    public void deleteQuestionWithNormalArguments() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        QuestionEntity question = new QuestionEntity();
        question.setQuestionText("text");
        question.setChapterEntity(chapter);
        chapterService.deleteQuestion(chapter, question);
        verify(chapterRepo, times(1)).save(any());
    }

    @Test(expected = NullPointerException.class)
    public void deleteAllQuestionsWithNullArguments() {
        chapterService.deleteAllQuestions(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteAllQuestionsWithIllegalArgument() {
        chapterService.deleteAllQuestions(new ChapterEntity());
    }

    @Test
    public void deleteAllQuestionsWithNormalArgument() {
        ChapterEntity chapter = new ChapterEntity();
        chapter.setChapterId(1);
        chapterService.deleteAllQuestions(chapter);
    }


}