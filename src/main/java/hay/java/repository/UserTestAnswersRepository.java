package hay.java.repository;

import hay.java.entity.ChapterEntity;
import hay.java.entity.QuestionEntity;
import hay.java.entity.UserEntity;
import hay.java.entity.UserQuestionAnswersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTestAnswersRepository extends JpaRepository<UserQuestionAnswersEntity, Long> {

    Optional<UserQuestionAnswersEntity> findByUserEntityAndQuestionEntity(UserEntity userEntity,
                                                                          QuestionEntity questionEntity);

    Page<UserQuestionAnswersEntity> findAllByUserEntityAndChapterEntity(UserEntity userEntity,
                                                                        ChapterEntity chapterEntity, Pageable pageable);
}
