package ru.irlix.learnit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.irlix.learnit.dto.request.TestRequest;
import ru.irlix.learnit.dto.response.test.TestFullResponse;
import ru.irlix.learnit.dto.response.test.TestResponse;
import ru.irlix.learnit.entity.Answer;
import ru.irlix.learnit.entity.Result;
import ru.irlix.learnit.entity.Test;
import ru.irlix.learnit.entity.Topic;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.entity.Variant;
import ru.irlix.learnit.service.helper.TopicHelper;
import ru.irlix.learnit.service.helper.UserHelper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class, ResultMapper.class, ImageMapper.class})
public abstract class TestMapper {

    @Autowired
    private TopicHelper topicHelper;

    @Autowired
    private UserHelper userHelper;

    public abstract Test mapToEntity(TestRequest testRequest);

    public abstract TestResponse mapToResponse(Test test);

    @Mapping(target = "author", source = "user")
    public abstract TestFullResponse mapToFullResponse(Test test);

    public abstract List<TestResponse> mapToResponseList(List<Test> tests);

    public Page<TestResponse> mapToResponsePage(Page<Test> tests) {
        List<TestResponse> content = mapToResponseList(tests.getContent());
        return new PageImpl<>(content, tests.getPageable(), tests.getTotalElements());
    }

    @AfterMapping
    protected void map(@MappingTarget Test test, TestRequest testRequest) {
        Topic topic = topicHelper.findTopicById(testRequest.getTopicId());
        test.setTopic(topic);
        UserData currentUserData = userHelper.getCurrentUserData();
        test.setUser(currentUserData);
    }

    @AfterMapping
    protected void map(@MappingTarget TestResponse response, Test test) {
        Long topicId = test.getTopic().getId();
        response.setTopicId(topicId);
        int questionCount = test.getQuestions().size();
        response.setQuestionsCount(questionCount);
        int resultCount = test.getResults().size();
        response.setResultCount(resultCount);
        Long authorId = test.getUser().getId();
        response.setAuthorId(authorId);
        int rightAnsweredCount = getRightAnsweredCount(test);
        int answeredCount = test.getResults().stream().filter(result -> result.getUser()
                        .equals(userHelper.getCurrentUserData()))
                .max(Comparator.comparingInt(r -> r.getAnswers().size()))
                .map(result -> result.getAnswers().size()).orElse(0);
        response.setQuestionsAnsweredCount(answeredCount);
        response.setQuestionsRightAnsweredCount(rightAnsweredCount);
        if (questionCount <= 0) {
            questionCount = 1;
        }
        long progress = Math.round((double) rightAnsweredCount / questionCount * 100);
        response.setProgress(progress);
    }

    private int getRightAnsweredCount(Test test) {
        List<Result> currentUserResults = test.getResults().stream().filter(result -> result.getUser()
                .equals(userHelper.getCurrentUserData())).collect(Collectors.toList());
        int rightAnsweredCount = 0;
        for (Result currentUserResult : currentUserResults) {
            int count = 0;
            for (Answer answer : currentUserResult.getAnswers()) {
                for (Variant variant : answer.getVariants()) {
                    if (variant.getIsRight())
                        count++;
                }
            }
            if (rightAnsweredCount < count)
                rightAnsweredCount = count;
        }
        return rightAnsweredCount;
    }

    @AfterMapping
    protected void map(@MappingTarget TestFullResponse response, Test test) {
        Long topicId = test.getTopic().getId();
        response.setTopicId(topicId);
    }
}
