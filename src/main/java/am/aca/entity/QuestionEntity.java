package am.aca.entity;

import am.aca.dto.QuestionDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "questions", schema = "oracle_exams",
        indexes = {
                @Index(name = "questions_chapter_id_index",
                        columnList = "chapter_id"),
                @Index(name = "questions_question_id_uindex",
                        unique = true,
                        columnList = "question_id")
        })
@Data
@NoArgsConstructor
public class QuestionEntity implements Serializable {
    public QuestionEntity(ChapterEntity chapterEntity, String questionText) {
        setChapterEntity(chapterEntity);
        setQuestionText(questionText);
    }

    public QuestionEntity(QuestionDto questionDto) {
        setQuestionText(questionDto.getQuestionText());
        setQuestionCode(questionDto.getQuestionCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionEntity)) return false;
        QuestionEntity that = (QuestionEntity) o;
        return getQuestionId() == that.getQuestionId() &&
                getQuestionText().equals(that.getQuestionText()) &&
                getQuestionCode().equals(that.getQuestionCode()) &&
                getChapterEntity() == null && that.getChapterEntity() == null ||
                getChapterEntity().getChapterId() == that.getChapterEntity().getChapterId() &&
                getAnswerEntityList().equals(that.getAnswerEntityList()) &&
                getClarificationEntity().getClarification_id() == that.getClarificationEntity().getClarification_id();
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", updatable = false)
    private int questionId;

    @Column(name = "question_text", length = 1000, nullable = false)
    private String questionText;

    @Column(name = "question_code", length = 1000)
    private String questionCode;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "chapter_id", referencedColumnName = "chapter_id")
    private ChapterEntity chapterEntity;

    @ToString.Exclude
    @OneToMany(mappedBy = "questionEntity", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<AnswerEntity> answerEntityList = new ArrayList<>();
    @ToString.Exclude
    @OneToOne(mappedBy = "questionEntity", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private ClarificationEntity clarificationEntity;
}
