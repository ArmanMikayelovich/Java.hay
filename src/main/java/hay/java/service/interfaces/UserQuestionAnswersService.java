package hay.java.service.interfaces;

import hay.java.dto.UserQuestionAnswerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQuestionAnswersService {
    UserQuestionAnswerDto add(UserQuestionAnswerDto userQuestionAnswerDto);

    UserQuestionAnswerDto getAnswerForOneQuestion(Long userId, Long questionId);

    public Page<UserQuestionAnswerDto> getQuestionsAndAnswersFromChapterForUser(Long userId, Long chapterId,
                                                                                Pageable pageable);


}
