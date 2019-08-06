package am.aca.service;

import am.aca.entity.ClarificationEntity;

import java.util.Optional;

public interface ClarificationService {

    ClarificationEntity save(ClarificationEntity clarificationEntity);

    Optional<ClarificationEntity> findOneById(Integer id);

    ClarificationEntity changeText(ClarificationEntity clarificationEntity, String text);

    boolean deleteById(Integer id);

    boolean delete(ClarificationEntity clarificationEntity);

    boolean deleteAll();


}
