package ru.irlix.learnit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.irlix.learnit.dto.request.NestedVariant;
import ru.irlix.learnit.dto.request.VariantRequest;
import ru.irlix.learnit.dto.response.variant.VariantResponse;
import ru.irlix.learnit.entity.Question;
import ru.irlix.learnit.entity.Variant;
import ru.irlix.learnit.service.helper.QuestionHelper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class VariantMapper {

    @Autowired
    private QuestionHelper questionHelper;

    public abstract Variant mapToVariant(VariantRequest variantRequest);

    public abstract Variant mapToVariant(NestedVariant variant);

    public abstract VariantResponse mapToResponse(Variant variant);

    public abstract List<VariantResponse> mapToResponseList(List<Variant> variantList);

    @AfterMapping
    protected void map(@MappingTarget Variant variant, VariantRequest variantRequest) {
        Question question = questionHelper.findQuestionById(variantRequest.getQuestionId());
        variant.setQuestion(question);
    }

    @AfterMapping
    protected void map(@MappingTarget VariantResponse variantResponse, Variant variant) {
        variantResponse.setQuestionId(variant.getQuestion().getId());
    }
}
