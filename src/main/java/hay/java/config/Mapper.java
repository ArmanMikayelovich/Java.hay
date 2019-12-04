package hay.java.config;

import hay.java.dto.*;
import hay.java.entity.*;
import hay.java.repository.*;
import hay.java.service.util.exception.*;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class Mapper extends ConfigurableMapper {
    private final TopicRepository topicRepository;
    private final ChapterRepository chapterRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public Mapper(TopicRepository topicRepository, ChapterRepository chapterRepository,
                  AnswerRepository answerRepository, QuestionRepository questionRepository,
                  UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.chapterRepository = chapterRepository;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(MapperFactory factory) {
        configureUser(factory);
        configureTopic(factory);
        configureChapter(factory);
        configureChapterItem(factory);
        configureQuestion(factory);
        configureAnswer(factory);
        configureClarification(factory);
        configureTestResults(factory);
        configureUserQuestionAnswers(factory);

    }

    private void configureUser(MapperFactory factory) {
        factory.classMap(UserEntity.class, UserDto.class)
                .byDefault()
                .customize(new CustomMapper<UserEntity, UserDto>() {
                    @Override
                    public void mapAtoB(UserEntity userEntity, UserDto userDto, MappingContext context) {
                        userDto.setPassword(null);
                    }
                }).register();
    }

    private void configureTopic(MapperFactory factory) {
        factory.classMap(TopicEntity.class, TopicDto.class)
                .byDefault()
                .register();
    }

    private void configureChapter(MapperFactory factory) {
        factory.classMap(ChapterEntity.class, ChapterDto.class)
                .customize(new CustomMapper<ChapterEntity, ChapterDto>() {
                    @Override
                    @Transactional(readOnly = true)
                    public void mapAtoB(ChapterEntity chapterEntity, ChapterDto chapterDto, MappingContext context) {
                        chapterDto.setChapterId(chapterEntity.getId());
                        chapterDto.setChapterName(chapterEntity.getChapterName());
                        chapterDto.setTopicId(chapterEntity.getTopicEntity().getId());
                    }

                    @Override
                    @Transactional(readOnly = true)
                    public void mapBtoA(ChapterDto chapterDto, ChapterEntity chapterEntity, MappingContext context) {
                        chapterEntity.setId(chapterDto.getChapterId());
                        chapterEntity.setChapterName(chapterDto.getChapterName());
                        chapterEntity.setTopicEntity(topicRepository.findById(chapterDto.getTopicId())
                                .orElseThrow(TopicNotFoundException::new));

                    }
                }).register();
    }

    private void configureChapterItem(MapperFactory factory) {
        factory.classMap(ChapterItemEntity.class, ChapterItemDto.class)
                .byDefault()
                .customize(new CustomMapper<ChapterItemEntity, ChapterItemDto>() {
                    @Override
                    @Transactional(readOnly = true)
                    public void mapAtoB(ChapterItemEntity itemEntity, ChapterItemDto chapterItemDto,
                                        MappingContext context) {
                        chapterItemDto.setChapterId(itemEntity.getChapterEntity().getId());
                    }

                    @Override
                    public void mapBtoA(ChapterItemDto chapterItemDto, ChapterItemEntity itemEntity, MappingContext context) {
                        itemEntity.setChapterEntity(chapterRepository.findById(chapterItemDto.getChapterId()).orElseThrow(ChapterItemNotFoundException::new));
                    }
                }).register();
    }

    private void configureQuestion(MapperFactory factory) {
        factory.classMap(QuestionEntity.class, QuestionDto.class)
                .byDefault()
                .customize(new CustomMapper<QuestionEntity, QuestionDto>() {
                    @Override
                    public void mapAtoB(QuestionEntity questionEntity, QuestionDto questionDto, MappingContext context) {
                        questionDto.setChapterId(questionEntity.getChapterEntity().getId());
                    }

                }).register();
    }

    private void configureAnswer(MapperFactory factory) {
        factory.classMap(AnswerEntity.class, AnswerDto.class)
                .byDefault()
                .customize(new CustomMapper<AnswerEntity, AnswerDto>() {
                    @Override
                    public void mapAtoB(AnswerEntity answerEntity, AnswerDto answerDto, MappingContext context) {
                        answerDto.setQuestionId(answerEntity.getQuestionEntity().getId());
                    }
                })
                .register();
    }

    private void configureClarification(MapperFactory factory) {
        factory.classMap(ClarificationEntity.class, ClarificationDto.class)
                .byDefault()
                .customize(new CustomMapper<ClarificationEntity, ClarificationDto>() {
                    @Override
                    public void mapAtoB(ClarificationEntity clarificationEntity, ClarificationDto clarificationDto, MappingContext context) {
                        clarificationDto.setQuestionId(clarificationEntity.getQuestionEntity().getId());
                    }
                }).register();
    }

    private void configureTestResults(MapperFactory factory) {
        factory.classMap(TestResultsEntity.class, TestResultsDto.class)
                .byDefault()
                .customize(new CustomMapper<TestResultsEntity, TestResultsDto>() {
                    @Override
                    public void mapAtoB(TestResultsEntity testResultsEntity, TestResultsDto testResultsDto, MappingContext context) {
                        testResultsDto.setUserId(testResultsEntity.getUserEntity().getId());
                        testResultsDto.setChapterId(testResultsEntity.getChapterEntity().getId());
                    }

                }).register();
    }

    private void configureUserQuestionAnswers(MapperFactory factory) {
        factory.classMap(UserQuestionAnswersEntity.class, UserQuestionAnswerDto.class)
                .byDefault()
                .customize(new CustomMapper<UserQuestionAnswersEntity, UserQuestionAnswerDto>() {
                    @Override
                    public void mapAtoB(UserQuestionAnswersEntity userQuestionAnswersEntity, UserQuestionAnswerDto userQuestionAnswerDto, MappingContext context) {
                        userQuestionAnswerDto.setUserId(userQuestionAnswersEntity.getUserEntity().getId());
                        userQuestionAnswerDto.setAnswerId(userQuestionAnswersEntity.getAnswerEntity().getAnswerId());
                        userQuestionAnswerDto.setChapterId(userQuestionAnswersEntity.getChapterEntity().getId());
                        userQuestionAnswerDto.setQuestionId(userQuestionAnswersEntity.getQuestionEntity().getId());
                    }

                    @Override
                    public void mapBtoA(UserQuestionAnswerDto userQuestionAnswerDto, UserQuestionAnswersEntity userQuestionAnswersEntity, MappingContext context) {
                        userQuestionAnswersEntity.setAnswerEntity(
                                answerRepository.findById(userQuestionAnswerDto.getAnswerId())
                                        .orElseThrow(AnswerNotFoundException::new));
                        userQuestionAnswersEntity.setChapterEntity(
                                chapterRepository.findById(userQuestionAnswerDto.getChapterId())
                                        .orElseThrow(ChapterNotFoundException::new));
                        userQuestionAnswersEntity.setQuestionEntity(
                                questionRepository.findById(userQuestionAnswerDto.getQuestionId())
                                        .orElseThrow(QuestionNotFoundException::new));
                        userQuestionAnswersEntity.setUserEntity(
                                userRepository.findById(userQuestionAnswerDto.getUserId())
                                        .orElseThrow(UserNotFoundException::new));

                    }

                }).register();
    }
}
