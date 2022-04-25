package ru.irlix.learnit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.irlix.learnit.dto.request.AnswerRequest;
import ru.irlix.learnit.dto.response.answer.AnswerFullResponse;
import ru.irlix.learnit.dto.response.answer.AnswerResponse;
import ru.irlix.learnit.dto.response.answer.FinalAnswerResponse;
import ru.irlix.learnit.dto.response.answer.NestedAnswer;
import ru.irlix.learnit.dto.response.variant.VariantWithAnswerResponse;
import ru.irlix.learnit.entity.Answer;
import ru.irlix.learnit.entity.Question;
import ru.irlix.learnit.entity.Result;
import ru.irlix.learnit.entity.Variant;
import ru.irlix.learnit.service.helper.QuestionHelper;
import ru.irlix.learnit.service.helper.ResultHelper;
import ru.irlix.learnit.service.helper.VariantHelper;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring", uses = {VariantMapper.class, QuestionMapper.class})
public abstract class AnswerMapper {

    @Autowired
    private QuestionHelper questionHelper;

    @Autowired
    private VariantHelper variantHelper;

    @Autowired
    private ResultHelper resultHelper;

    @Mapping(target = "variants", ignore = true)
    public abstract Answer mapToEntity(AnswerRequest request);

    public abstract AnswerFullResponse mapToFullResponse(Answer answer);

    public abstract NestedAnswer mapToNested(Answer answer);

    @Mapping(target = "variants", source = "question.variants")
    public abstract FinalAnswerResponse mapToFinalResponse(Answer answer);

    public abstract List<AnswerResponse> mapToResponseList(List<Answer> answers);

    public abstract List<FinalAnswerResponse> mapToFinalResponseList(List<Answer> answers);

    @AfterMapping
    protected void map(@MappingTarget Answer answer, AnswerRequest request) {
        Question question = questionHelper.findQuestionById(request.getQuestionId());
        answer.setQuestion(question);
        Result result = resultHelper.findResultById(request.getResultId());
        answer.setResult(result);
        List<Variant> variants = variantHelper.findVariantsByQuestionIdAndIdIn(question.getId(), request.getVariants());
        answer.setVariants(variants);
    }

    @AfterMapping
    protected void map(@MappingTarget AnswerResponse response, Answer answer) {
        Long questionId = answer.getQuestion().getId();
        response.setQuestionId(questionId);
        Long resultId = answer.getResult().getId();
        response.setResultId(resultId);
        int variantsCount = answer.getVariants().size();
        response.setVariantsCount(variantsCount);
    }

    @AfterMapping
    protected void map(@MappingTarget AnswerFullResponse response, Answer answer) {
        Long questionId = answer.getQuestion().getId();
        response.setQuestionId(questionId);
        Long resultId = answer.getResult().getId();
        response.setResultId(resultId);
    }

    @AfterMapping
    protected void map(@MappingTarget NestedAnswer nestedAnswer, Answer answer) {
        Long questionId = answer.getQuestion().getId();
        nestedAnswer.setQuestionId(questionId);
        List<Long> variantsId = answer.getVariants().stream()
                .map(Variant::getId)
                .toList();
        nestedAnswer.setSelectedVariants(variantsId);
    }

    @AfterMapping
    protected void map(@MappingTarget FinalAnswerResponse response, Answer answer) {
        List<Variant> selectedVariants = answer.getVariants();
        response.getVariants().forEach(e -> setSelected(e , selectedVariants));
    }

    private void setSelected(VariantWithAnswerResponse variant, List<Variant> selectedVariants) {
        boolean selected = selectedVariants.stream()
                .anyMatch(e -> Objects.equals(e.getId(), variant.getId()));
        variant.setIsSelected(selected);
    }
}
