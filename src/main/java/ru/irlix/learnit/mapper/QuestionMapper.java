package ru.irlix.learnit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.irlix.learnit.dto.request.QuestionRequest;
import ru.irlix.learnit.dto.response.question.NestedQuestion;
import ru.irlix.learnit.dto.response.question.QuestionResponse;
import ru.irlix.learnit.entity.Question;
import ru.irlix.learnit.entity.Test;
import ru.irlix.learnit.entity.Variant;
import ru.irlix.learnit.exception.NoRightVariantInQuestionException;
import ru.irlix.learnit.service.helper.TestHelper;

import java.util.List;

@Mapper(componentModel = "spring", uses = VariantMapper.class)
public abstract class QuestionMapper {

    @Autowired
    private TestHelper testHelper;

    public abstract Question mapToEntity(QuestionRequest questionRequest);

    public abstract QuestionResponse mapToResponse(Question question);

    public abstract NestedQuestion mapToNested(Question question);

    public abstract List<QuestionResponse> mapToResponseList(List<Question> questionList);

    @AfterMapping
    protected void map(@MappingTarget Question question, QuestionRequest questionRequest) {
        Test test = testHelper.findTestById(questionRequest.getTestId());
        question.setTest(test);
        boolean haveAnyRightVariant = question.getVariants().stream().anyMatch(this::isRight);
        if (!haveAnyRightVariant) {
            throw new NoRightVariantInQuestionException();
        }
        question.getVariants().forEach(v -> v.setQuestion(question));
    }

    @AfterMapping
    protected void map(@MappingTarget QuestionResponse questionResponse, Question question) {
        questionResponse.setTestId(question.getTest().getId());
    }

    private boolean isRight(Variant variant) {
        if (variant.getIsRight() != null) {
            return variant.getIsRight();
        }
        return false;
    }
}
