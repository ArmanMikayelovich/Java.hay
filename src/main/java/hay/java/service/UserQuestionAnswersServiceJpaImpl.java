package hay.java.service;

import hay.java.config.Mapper;
import hay.java.dto.UserQuestionAnswerDto;
import hay.java.entity.ChapterEntity;
import hay.java.entity.QuestionEntity;
import hay.java.entity.UserEntity;
import hay.java.entity.UserQuestionAnswersEntity;
import hay.java.repository.ChapterRepository;
import hay.java.repository.QuestionRepository;
import hay.java.repository.UserRepository;
import hay.java.repository.UserTestAnswersRepository;
import hay.java.service.interfaces.UserQuestionAnswersService;
import hay.java.service.util.exception.ChapterNotFoundException;
import hay.java.service.util.exception.QuestionNotFoundException;
import hay.java.service.util.exception.UserNotFoundException;
import hay.java.service.util.exception.UserQuestionAnswersNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@Transactional(readOnly = true)
public class UserQuestionAnswersServiceJpaImpl implements UserQuestionAnswersService {
    private final UserRepository userRepo;
    private final UserTestAnswersRepository testRepo;
    private final QuestionRepository questionRepository;
    private final ChapterRepository chapterRepository;
    private final Mapper mapper;

    public UserQuestionAnswersServiceJpaImpl(UserRepository userRepo, UserTestAnswersRepository testRepo,
                                             QuestionRepository questionRepository,
                                             ChapterRepository chapterRepository, Mapper mapper) {
        this.userRepo = userRepo;
        this.testRepo = testRepo;
        this.questionRepository = questionRepository;

        this.chapterRepository = chapterRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public UserQuestionAnswerDto add(@Valid UserQuestionAnswerDto userQuestionAnswerDto) {
        UserQuestionAnswersEntity userQuestionAnswersEntity =
                mapper.map(userQuestionAnswerDto, UserQuestionAnswersEntity.class);
        testRepo.save(userQuestionAnswersEntity);
        mapper.map(userQuestionAnswersEntity, userQuestionAnswerDto);
        return userQuestionAnswerDto;
    }

    @Override
    public UserQuestionAnswerDto getAnswerForOneQuestion(Long userId, Long questionId) {
        UserEntity userEntity = userRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        QuestionEntity questionEntity = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        UserQuestionAnswersEntity userQuestionAnswersEntity =
                testRepo.findByUserEntityAndQuestionEntity(userEntity, questionEntity)
                        .orElseThrow(UserQuestionAnswersNotFoundException::new);
        return mapper.map(userQuestionAnswersEntity, UserQuestionAnswerDto.class);
    }

    @Override
    public Page<UserQuestionAnswerDto> getQuestionsAndAnswersFromChapterForUser(Long userId, Long chapterId,
                                                                                Pageable pageable) {
        UserEntity userEntity = userRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        ChapterEntity chapterEntity = chapterRepository.findById(chapterId).orElseThrow(ChapterNotFoundException::new);
        Page<UserQuestionAnswersEntity> userQuestionAnswersEntities =
                testRepo.findAllByUserEntityAndChapterEntity(userEntity, chapterEntity, pageable);
        return userQuestionAnswersEntities.map(userQuestionAnswersEntity ->
                mapper.map(userQuestionAnswersEntity, UserQuestionAnswerDto.class));
    }
}
