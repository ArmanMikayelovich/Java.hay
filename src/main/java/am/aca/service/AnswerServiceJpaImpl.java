package am.aca.service;

import am.aca.dto.AnswerDto;
import am.aca.entity.AnswerEntity;
import am.aca.repository.AnswerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerServiceJpaImpl implements AnswerService {
    private static final Logger log = LogManager.getLogger(AnswerServiceJpaImpl.class);

    private final AnswerRepository answerRepo;

    public AnswerServiceJpaImpl(AnswerRepository answerRepo) {
        this.answerRepo = answerRepo;
    }

    @Override
    public AnswerEntity save(AnswerEntity answerEntity) {
        log.debug("saving " + answerEntity);
        AnswerEntity save = answerRepo.save(answerEntity);
        log.debug(save + "saved");
        return save;
    }

    @Override
    public Optional<AnswerEntity> findById(Integer id) {
        log.debug("Searching answer by id " + id);

        Optional<AnswerEntity> byId = answerRepo.findById(id);
        if (byId.isPresent()) {
            log.debug("answer with id " + id + "found. " + byId.get());
        } else {
            log.debug("answer with id " + id+ "not found");
        }
        return byId;
    }

    @Override
    public List<AnswerEntity> findAll() {
        log.debug("Requested All answers.");
        return answerRepo.findAll();
    }

    @Override
    public boolean delete(AnswerEntity answerEntity) {
        log.debug("deleting " + answerEntity);

        answerRepo.delete(answerEntity);
        log.debug(answerEntity + "deleted.");
        return true;
    }

    @Override
    public boolean deleteAll() {
        log.debug("Deleting all answers.");
        answerRepo.deleteAll();
        log.debug("All answers deleted.");
        return true;
    }

    @Override
    public AnswerEntity changeText(AnswerEntity answer, String text) {
        log.debug("Changing text of " + answer + " to " + text);
        answer.setAnswerText(text);
        AnswerEntity save = answerRepo.save(answer);
        log.debug("Answer changed " + save);
        return save;
    }

    @Override
    public AnswerEntity changeCode(AnswerEntity answer, String code) {
        log.debug("Changing code of " + answer + " to " + code);
        answer.setAnswerCode(code);
        AnswerEntity save = answerRepo.save(answer);
        log.debug("Answer changed " + save);
        return save;
    }

    @Override
    public AnswerEntity changeAccuracy(AnswerEntity answer, boolean accuracy) {
        log.debug("changing accuracy of " + answer + " to " + accuracy);
        answer.setAccuracy(accuracy);
        AnswerEntity save = answerRepo.save(answer);
        log.debug("Answer changed " + save);
        return save;
    }

    @Override
    public List<AnswerDto> toDto(List<AnswerEntity> answerEntityList) {
        return answerEntityList.stream().map(AnswerDto::new).collect(Collectors.toList());
    }
}
