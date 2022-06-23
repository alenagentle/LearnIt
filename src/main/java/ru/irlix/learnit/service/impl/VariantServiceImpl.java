package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.dto.request.VariantRequest;
import ru.irlix.learnit.dto.response.variant.VariantResponse;
import ru.irlix.learnit.entity.Question;
import ru.irlix.learnit.entity.Variant;
import ru.irlix.learnit.mapper.VariantMapper;
import ru.irlix.learnit.repository.VariantRepository;
import ru.irlix.learnit.service.api.VariantService;
import ru.irlix.learnit.service.helper.QuestionHelper;
import ru.irlix.learnit.service.helper.VariantHelper;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VariantServiceImpl implements VariantService {

    private final VariantRepository variantRepository;
    private final VariantMapper variantMapper;
    private final VariantHelper variantHelper;
    private final QuestionHelper questionHelper;

    @Override
    @Transactional
    public VariantResponse createVariant(VariantRequest variantRequest) {
        Variant variant = variantMapper.mapToVariant(variantRequest);
        Variant savedVariant = variantRepository.save(variant);
        log.info("New variant created. Id = {}", savedVariant.getId());
        return variantMapper.mapToResponse(savedVariant);
    }

    @Override
    @Transactional(readOnly = true)
    public VariantResponse findVariantById(Long id) {
        Variant variant = variantHelper.findVariantById(id);
        return variantMapper.mapToResponse(variant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VariantResponse> findVariantsByQuestionId(Long questionId) {
        questionHelper.checkQuestionExistingById(questionId);
        List<Variant> variants = variantRepository.findVariantsByQuestionId(questionId);
        return variantMapper.mapToResponseList(variants);
    }

    @Override
    @Transactional
    public VariantResponse updateVariant(Long id, VariantRequest variantRequest) {
        Variant variant = variantHelper.findVariantById(id);
        checkAndUpdateFields(variant, variantRequest);
        Variant updatedVariant = variantRepository.save(variant);
        log.info("Variant with id {} updated", id);
        return variantMapper.mapToResponse(updatedVariant);
    }

    @Override
    @Transactional
    public void deleteVariant(Long id) {
        Variant variant = variantHelper.findVariantById(id);
        variantRepository.delete(variant);
        log.info("Variant with id {} deleted", id);
    }

    private void checkAndUpdateFields(Variant variant, VariantRequest variantRequest) {
        if (variantRequest.getText() != null) {
            variant.setText(variantRequest.getText());
        }
        if (variantRequest.getIsRight() != null) {
            variant.setIsRight(variantRequest.getIsRight());
        }
        if (variantRequest.getQuestionId() != null) {
            Question question = questionHelper.findQuestionById(variantRequest.getQuestionId());
            variant.setQuestion(question);
        }
    }
}
