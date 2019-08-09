package am.aca.service;

import am.aca.dto.AnswerDto;
import am.aca.entity.AnswerEntity;
import am.aca.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerServiceJpaImpl implements AnswerService {
    private final AnswerRepository answerRepo;

    public AnswerServiceJpaImpl(AnswerRepository answerRepo) {
        this.answerRepo = answerRepo;
    }

    @Override
    public AnswerEntity save(AnswerEntity answerEntity) {
        return answerRepo.save(answerEntity);
    }

    @Override
    public Optional<AnswerEntity> findById(Integer id) {
        return answerRepo.findById(id);
    }

    @Override
    public List<AnswerEntity> findAll() {
        return answerRepo.findAll();
    }

    @Override
    public boolean delete(AnswerEntity answerEntity) {
        answerRepo.delete(answerEntity);
        return true;
    }

    @Override
    public boolean deleteAll() {
        answerRepo.deleteAll();
        return true;
    }

    @Override
    public AnswerEntity changeText(AnswerEntity answer, String text) {
        answer.setAnswerText(text);
        return answerRepo.save(answer);
    }

    @Override
    public AnswerEntity changeCode(AnswerEntity answer, String code) {
        answer.setAnswerCode(code);
        return answerRepo.save(answer);
    }

    @Override
    public AnswerEntity changeAccuracy(AnswerEntity answer, boolean accuracy) {
        answer.setAccuracy(accuracy);
        return answerRepo.save(answer);
    }

    @Override
    public List<AnswerDto> toDto(List<AnswerEntity> answerEntityList) {
        return answerEntityList.stream().map(AnswerDto::new).collect(Collectors.toList());
    }
}
