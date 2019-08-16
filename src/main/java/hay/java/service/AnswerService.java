package hay.java.service;

import hay.java.dto.AnswerDto;
import hay.java.entity.AnswerEntity;

import java.util.List;
import java.util.Optional;

public interface AnswerService {

    AnswerEntity save(AnswerEntity answerEntity);

    Optional<AnswerEntity> findById(Integer id);

    List<AnswerEntity> findAll();

    boolean delete(AnswerEntity answerEntity);

    boolean deleteAll();

    AnswerEntity changeText(AnswerEntity answer, String text);

    AnswerEntity changeCode(AnswerEntity answer, String code);

    AnswerEntity changeAccuracy(AnswerEntity answer, boolean accuracy);

    List<AnswerDto> toDto(List<AnswerEntity> answerEntityList);
}
