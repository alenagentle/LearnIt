package ru.irlix.learnit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.irlix.learnit.dto.request.ResultRequest;
import ru.irlix.learnit.dto.response.result.ResultResponse;
import ru.irlix.learnit.dto.response.result.FinalResultResponse;
import ru.irlix.learnit.entity.Result;
import ru.irlix.learnit.entity.Test;
import ru.irlix.learnit.service.helper.TestHelper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AnswerMapper.class, TestMapper.class})
public abstract class ResultMapper {

    @Autowired
    private TestHelper testHelper;

    public abstract Result mapToEntity(ResultRequest resultRequest);

    public abstract ResultResponse mapToResponse(Result result);

    public abstract FinalResultResponse mapToFinalResponse(Result result);

    public abstract List<ResultResponse> mapToResponseList(List<Result> results);

    @AfterMapping
    protected void map(@MappingTarget ResultResponse response, Result result) {
        Long testId = result.getTest().getId();
        response.setTestId(testId);
    }

    @AfterMapping
    protected void map(@MappingTarget Result result, ResultRequest request) {
        Test test = testHelper.findTestById(request.getTestId());
        result.setTest(test);
    }
}
