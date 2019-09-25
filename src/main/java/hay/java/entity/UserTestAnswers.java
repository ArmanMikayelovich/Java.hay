package hay.java.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_test_answers", indexes = {
        @Index(name = "user_test_answers_ID_index", columnList = "user_id"),
        @Index(name = "user_test_answers_chapter_ID_index", columnList = "chapter_id")
})
@Data
@NoArgsConstructor
public class UserTestAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;


    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private ChapterEntity chapterEntity;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity questionEntity;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private AnswerEntity answerEntity;

}
