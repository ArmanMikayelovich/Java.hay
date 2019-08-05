package am.aca;

import am.aca.entity.*;
import am.aca.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication
public class App implements CommandLineRunner {
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    ChapterItemRepository chapterItemRepository;
    @Autowired
    ClarificationRepository clarificaionRepository;
    @Autowired
    QuestionRepository questionRepository;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String[] args) {
        for (int x = 0; x < 10; ++x) {
            System.out.println();
        }

        TopicEntity ocaTopic = new TopicEntity("OCA");
        topicRepository.save(ocaTopic);

        ChapterEntity firstChapter = new ChapterEntity(ocaTopic, "first chapter");

//        firstChapter.setTopicEntity(ocaTopic);
        chapterRepository.save(firstChapter);

        ChapterItemEntity chapterItem = new ChapterItemEntity(firstChapter,
                "Headline of first chapter's first item...");
        chapterItemRepository.save(chapterItem);

        QuestionEntity questionEntity = new QuestionEntity(firstChapter, "how are you, my brother?)");

        questionEntity.setQuestionCode("lot of code");
        questionRepository.save(questionEntity);

        AnswerEntity answerEntity = new AnswerEntity(
                questionEntity, "Great, thank you!", "A", true);
        answerRepository.save(answerEntity);

        ClarificationEntity clarificationEntity = new ClarificationEntity(questionEntity,"You are TrueMAN!)");
        clarificaionRepository.save(clarificationEntity);

        questionEntity = questionRepository.findById(questionEntity.getQuestionId()).get();
        questionEntity.getAnswerEntityList().add(answerEntity);
        questionEntity.setClarificationEntity(clarificationEntity);
        questionRepository.save(questionEntity);


    }


}
