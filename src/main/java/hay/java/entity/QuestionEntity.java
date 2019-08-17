package hay.java.entity;

import hay.java.dto.QuestionDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "questions", /*schema = "oracle_exams",*/
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


    public QuestionEntity(QuestionDto questionDto) {
        setQuestionText(questionDto.getQuestionText());
        setQuestionCode(questionDto.getQuestionCode());
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", updatable = false)
    private int questionId;

    @Column(name = "question_text", length = 1000, nullable = false)
    private String questionText;

    @Column(name = "question_code", length = 1000)
    private String questionCode;

    @ManyToOne
    @JoinColumn(name = "chapter_id", referencedColumnName = "chapter_id")
    private ChapterEntity chapterEntity;

    @OneToMany(mappedBy = "questionEntity", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<AnswerEntity> answerEntityList = new ArrayList<>();
    @OneToOne(mappedBy = "questionEntity", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private ClarificationEntity clarificationEntity;

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
                        getClarificationEntity().getClarificationId() == that.getClarificationEntity().getClarificationId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuestionId(), getQuestionText(), getQuestionCode(),
                getChapterEntity().getChapterId(), getAnswerEntityList(), getClarificationEntity());
    }

    @Override
    public String toString() {
        return "QuestionEntity{" +
                "questionId=" + questionId +
                ", questionText='" + questionText + '\'' +
                ", questionCode='" + questionCode + '\'' +
                ", chapterId=" + (chapterEntity != null ? chapterEntity.getChapterId() : "null") +
                ", answerEntityList.size=" + answerEntityList.size() +
                ", clarificationId=" +(clarificationEntity!= null ? clarificationEntity.getClarificationId() : "null") +
                '}';
    }
}
