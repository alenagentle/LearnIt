package ru.irlix.learnit.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.entity.Variant;
import ru.irlix.learnit.exception.NotFoundException;
import ru.irlix.learnit.repository.VariantRepository;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class VariantHelper {

    private final VariantRepository variantRepository;

    public Variant findVariantById(Long id) {
        return variantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Variant with id %d not found", id)));
    }

    public List<Variant> findVariantsByQuestionIdAndIdIn(Long questionId, Collection<Long> variantsId) {
        return variantRepository.findVariantsByQuestionIdAndIdIn(questionId, variantsId);
    }

    public List<Variant> findRightVariantsByQuestionId(Long questionId) {
        return variantRepository.findVariantsByIsRightTrueAndQuestionId(questionId);
    }
}
