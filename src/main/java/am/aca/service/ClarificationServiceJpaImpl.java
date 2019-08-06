package am.aca.service;

import am.aca.entity.ClarificationEntity;
import am.aca.repository.ClarificationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ClarificationServiceJpaImpl implements ClarificationService {
    private final ClarificationRepository clarificationRepo;

    public ClarificationServiceJpaImpl(ClarificationRepository clarificationRepo) {
        this.clarificationRepo = clarificationRepo;
    }

    @Override
    public ClarificationEntity save(ClarificationEntity clarificationEntity) {
        return clarificationRepo.save(clarificationEntity);
    }

    @Override
    public Optional<ClarificationEntity> findOneById(Integer id) {
        return clarificationRepo.findById(id);
    }

    @Override
    public ClarificationEntity changeText(ClarificationEntity clarificationEntity, String text) {
        clarificationEntity.setClarificationText(text);
        return clarificationRepo.save(clarificationEntity);
    }

    @Override
    public boolean deleteById(Integer id) {
        clarificationRepo.deleteById(id);
        return true;
    }

    @Override
    public boolean delete(ClarificationEntity clarificationEntity) {
        clarificationRepo.delete(clarificationEntity);
        return true;
    }

    @Override
    public boolean deleteAll() {
        clarificationRepo.deleteAll();
        return true;
    }
}
