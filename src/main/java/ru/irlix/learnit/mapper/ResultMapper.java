package ru.irlix.learnit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.irlix.learnit.dto.request.ResultRequest;
import ru.irlix.learnit.dto.response.answer.FinalAnswerResponse;
import ru.irlix.learnit.dto.response.result.FinalResultResponse;
import ru.irlix.learnit.dto.response.result.NestedResultResponse;
import ru.irlix.learnit.dto.response.result.ResultFullResponse;
import ru.irlix.learnit.dto.response.result.ResultResponse;
import ru.irlix.learnit.dto.response.result.UsersResultResponse;
import ru.irlix.learnit.dto.response.test.TestWithResultResponse;
import ru.irlix.learnit.entity.Result;
import ru.irlix.learnit.entity.Test;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.service.helper.TestHelper;
import ru.irlix.learnit.service.helper.UserHelper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {AnswerMapper.class, ImageMapper.class})
public abstract class ResultMapper {

    @Autowired
    private TestHelper testHelper;

    @Autowired
    private UserHelper userHelper;

    public abstract Result mapToEntity(ResultRequest resultRequest);

    public abstract ResultFullResponse mapToFullResponse(Result result);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "testId", source = "test.id")
    public abstract ResultResponse mapToResponse(Result result);

    @Mapping(target = ".", source = "result")
    @Mapping(target = "answers", source = "nestedAnswers")
    public abstract FinalResultResponse mapToFinalResponse(Result result, Collection<FinalAnswerResponse> nestedAnswers);

    public abstract List<ResultResponse> mapToResponseList(List<Result> results);

    protected abstract List<TestWithResultResponse> mapToTestWithResultResponse(List<Test> tests);

    public UsersResultResponse mapToUsersResultResponse(List<Test> tests) {
        UsersResultResponse response = new UsersResultResponse();
        List<TestWithResultResponse> testResponses = mapToTestWithResultResponse(tests);
        response.setTests(testResponses);
        return response;
    }

    @AfterMapping
    protected void map(@MappingTarget ResultFullResponse response, Result result) {
        Long testId = result.getTest().getId();
        response.setTestId(testId);
    }

    @AfterMapping
    protected void map(@MappingTarget ResultResponse response, Result result) {
        int answersCount = result.getAnswers().size();
        response.setAnswersCount(answersCount);
    }

    @AfterMapping
    protected void map(@MappingTarget NestedResultResponse response, Result result) {
        int answersCount = result.getAnswers().size();
        response.setAnswersCount(answersCount);
    }

    @AfterMapping
    protected void map(@MappingTarget Result result, ResultRequest request) {
        Test test = testHelper.findTestById(request.getTestId());
        result.setTest(test);
        UserData currentUserData = userHelper.getCurrentUserData();
        result.setUser(currentUserData);
        result.getAnswers().forEach(answer -> answer.setResult(result));
    }
}
