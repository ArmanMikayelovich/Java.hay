package hay.java.service.interfaces;

import hay.java.dto.AnswerDto;
import hay.java.dto.ChapterDto;
import hay.java.dto.QuestionDto;
import hay.java.dto.UserDto;

import java.util.Map;
import java.util.Optional;

public interface UserTestAnswersService {
    boolean add(UserDto user, QuestionDto question, AnswerDto answer);

    Optional<AnswerDto> getAnswerForOneQuestion(UserDto user, QuestionDto question);

    Map<QuestionDto, AnswerDto> getAnswersForOneChapter(UserDto user, ChapterDto chapter);


}
