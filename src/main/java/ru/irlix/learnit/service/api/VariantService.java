package ru.irlix.learnit.service.api;

import ru.irlix.learnit.dto.request.VariantRequest;
import ru.irlix.learnit.dto.response.variant.VariantResponse;

import java.util.List;

public interface VariantService {

    VariantResponse createVariant(VariantRequest variantRequest);

    VariantResponse findVariantById(Long id);

    List<VariantResponse> findVariantsByQuestionId(Long id);

    VariantResponse updateVariant(Long id, VariantRequest variantRequest);

    void deleteVariant(Long id);
}