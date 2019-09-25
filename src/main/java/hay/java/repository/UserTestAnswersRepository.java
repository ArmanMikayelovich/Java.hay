package hay.java.repository;

import hay.java.entity.ChapterEntity;
import hay.java.entity.QuestionEntity;
import hay.java.entity.UserEntity;
import hay.java.entity.UserTestAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTestAnswersRepository extends JpaRepository<UserTestAnswers, Integer> {

    Optional<UserTestAnswers> findByUserEntityAndQuestionEntity(UserEntity userEntity, QuestionEntity questionEntity);
    List<UserTestAnswers> findAllByUserEntityAndChapterEntity(UserEntity userEntity, ChapterEntity chapterEntity);
}
