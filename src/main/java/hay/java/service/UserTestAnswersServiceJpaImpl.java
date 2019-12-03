package hay.java.service;

import hay.java.dto.AnswerDto;
import hay.java.dto.ChapterDto;
import hay.java.dto.QuestionDto;
import hay.java.dto.UserDto;
import hay.java.entity.*;
import hay.java.repository.UserRepository;
import hay.java.repository.UserTestAnswersRepository;
import hay.java.service.interfaces.AnswerService;
import hay.java.service.interfaces.ChapterService;
import hay.java.service.interfaces.QuestionService;
import hay.java.service.interfaces.UserTestAnswersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserTestAnswersServiceJpaImpl implements UserTestAnswersService {
    private final UserRepository userRepo;
    private final UserTestAnswersRepository testRepo;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final ChapterService chapterService;

    public UserTestAnswersServiceJpaImpl(UserRepository userRepo, UserTestAnswersRepository testRepo,
                                         QuestionService questionService, AnswerService answerService,
                                         ChapterService chapterService) {
        this.userRepo = userRepo;
        this.testRepo = testRepo;
        this.questionService = questionService;
        this.answerService = answerService;
        this.chapterService = chapterService;
    }

    @Override
    @Transactional
    public boolean add(UserDto user, QuestionDto question, AnswerDto answer) {
        Optional<UserEntity> userById = userRepo.findById(user.getId());
        if (!userById.isPresent()) return false;
        Optional<QuestionEntity> questionById = questionService.findById(question.getId());
        if (!questionById.isPresent()) return false;
        Optional<AnswerEntity> answerById = answerService.findById(answer.getId());
        if (!answerById.isPresent()) return false;

        UserTestAnswers test = new UserTestAnswers();
        test.setUserEntity(userById.get());
        test.setQuestionEntity(questionById.get());
        test.setChapterEntity(questionById.get().getChapterEntity());
        test.setAnswerEntity(answerById.get());
        testRepo.save(test);
        return true;
    }

    @Override
    @Transactional
    public Optional<AnswerDto> getAnswerForOneQuestion(UserDto user, QuestionDto question) {
        Optional<UserEntity> userById = userRepo.findById(user.getId());
        if (!userById.isPresent()) return Optional.empty();
        Optional<QuestionEntity> questionById = questionService.findById(question.getId());
        if (!questionById.isPresent()) return Optional.empty();
        Optional<UserTestAnswers> byUserEntityAndQuestionEntity =
                testRepo.findByUserEntityAndQuestionEntity(userById.get(), questionById.get());
        if (byUserEntityAndQuestionEntity.isPresent()) {
            AnswerDto answerDto = answerService.toDto(byUserEntityAndQuestionEntity.get().getAnswerEntity());
            return Optional.of(answerDto);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Map<QuestionDto, AnswerDto> getAnswersForOneChapter(UserDto user, ChapterDto chapter) {
        Optional<UserEntity> userById = userRepo.findById(user.getId());
        if (!userById.isPresent()) return Collections.emptyMap();
        Optional<ChapterEntity> chapterById = chapterService.findById(chapter.getChapterId());
        if (!chapterById.isPresent()) return Collections.emptyMap();
        List<UserTestAnswers> list = testRepo.findAllByUserEntityAndChapterEntity(userById.get(), chapterById.get());
        if (!list.isEmpty()) {
            Map<QuestionDto, AnswerDto> map = new HashMap<>();
            list.forEach(x -> map.put(questionService.toDto(x.getQuestionEntity()),
                    answerService.toDto(x.getAnswerEntity())));
            return map;
        }
        return Collections.emptyMap();
    }
}
