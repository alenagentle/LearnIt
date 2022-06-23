package ru.irlix.learnit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.irlix.learnit.dto.request.QuestionRequest;
import ru.irlix.learnit.dto.response.answer.FinalAnswerResponse;
import ru.irlix.learnit.dto.response.question.NestedQuestionResponse;
import ru.irlix.learnit.dto.response.question.QuestionResponse;
import ru.irlix.learnit.entity.Question;
import ru.irlix.learnit.entity.Test;
import ru.irlix.learnit.entity.Variant;
import ru.irlix.learnit.exception.NoRightVariantInQuestionException;
import ru.irlix.learnit.service.helper.TestHelper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {VariantMapper.class, ImageMapper.class})
public abstract class QuestionMapper {

    @Autowired
    private TestHelper testHelper;

    public abstract Question mapToEntity(QuestionRequest questionRequest);

    public abstract QuestionResponse mapToResponse(Question question);

    public abstract NestedQuestionResponse mapToNestedResponse(Question question);

    public abstract List<QuestionResponse> mapToResponseList(List<Question> questionList);

    @Mapping(target = "question", source = ".")
    @Mapping(target = "id", ignore = true)
    public abstract FinalAnswerResponse mapToFinalResponse(Question question);

    @AfterMapping
    protected void map(@MappingTarget Question question, QuestionRequest questionRequest) {
        validateRightVariantExisting(question);
        Test test = testHelper.findTestById(questionRequest.getTestId());
        question.setTest(test);
        if (question.getVariants() != null) {
            question.getVariants().forEach(v -> v.setQuestion(question));
        }
    }

    @AfterMapping
    protected void map(@MappingTarget QuestionResponse questionResponse, Question question) {
        questionResponse.setTestId(question.getTest().getId());
    }

    private void validateRightVariantExisting(Question question) {
        if (question.getVariants() != null) {
            boolean haveAnyRightVariant = question.getVariants().stream().anyMatch(this::isRight);
            if (!haveAnyRightVariant) {
                throw new NoRightVariantInQuestionException();
            }
        }
    }

    private boolean isRight(Variant variant) {
        if (variant.getIsRight() != null) {
            return variant.getIsRight();
        }
        return false;
    }
}
