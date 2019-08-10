package am.aca;

import am.aca.entity.*;
import am.aca.repository.*;
import am.aca.service.TopicSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

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
    final TopicSerice topicSerice;


    TopicEntity ocaTopic;
    ChapterEntity firstChapter;
    ChapterItemEntity chapterItem;
    QuestionEntity questionEntity;
    AnswerEntity answerEntity;
    ClarificationEntity clarificationEntity;

    public App(TopicSerice topicSerice) {
        this.topicSerice = topicSerice;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String[] args) {
//        transactionRunner();
//        topicSerice.deleteChapter(ocaTopic, firstChapter);
    }

    @Transactional(readOnly = true)
    public void readFields() {
        System.out.println(ocaTopic);
    }
    @Transactional
    public void transactionRunner() {

        for (int x = 0; x < 10; ++x) {
            System.out.println();
        }

//        ocaTopic = new TopicEntity("OCA");
//         firstChapter = new ChapterEntity("namo");
//        topicSerice.addChapter(ocaTopic, firstChapter);
//        firstChapter = new ChapterEntity(ocaTopic, "first chapter");
//
////        firstChapter.setTopicEntity(ocaTopic);
//        chapterRepository.save(firstChapter);
//
//        chapterItem = new ChapterItemEntity(firstChapter,
//                "Headline of first chapter's first item...");
//        chapterItemRepository.save(chapterItem);
//
//        questionEntity = new QuestionEntity(firstChapter, "how are you, my brother?)");
//
//        questionEntity.setQuestionCode("lot of code");
//        questionRepository.save(questionEntity);
//
//        answerEntity = new AnswerEntity(
//                questionEntity, "Great, thank you!", "A", true);
//        answerRepository.save(answerEntity);
//
//        clarificationEntity = new ClarificationEntity(questionEntity, "You are TrueMAN!)");
//        clarificaionRepository.save(clarificationEntity);
//
//        questionEntity = questionRepository.findById(questionEntity.getQuestionId()).get();
//        ocaTopic.getChapterEntityList().add(firstChapter);
//        ocaTopic = topicRepository.save(ocaTopic);

    }


}
