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
import ru.irlix.learnit.entity.Test;
import ru.irlix.learnit.entity.Topic;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.service.helper.TopicHelper;
import ru.irlix.learnit.service.helper.UserHelper;

import java.util.Comparator;
import java.util.List;

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
        int answeredCount = test.getResults().stream().filter(result -> result.getUser()
                        .equals(userHelper.getCurrentUserData()))
                .max(Comparator.comparingInt(r -> r.getAnswers().size()))
                .map(result -> result.getAnswers().size()).orElse(0);
        response.setQuestionsAnsweredCount(answeredCount);
        if (questionCount <= 0) {
            questionCount = 1;
        }
        int progress = answeredCount / questionCount * 100;
        response.setProgress(progress);
    }

    @AfterMapping
    protected void map(@MappingTarget TestFullResponse response, Test test) {
        Long topicId = test.getTopic().getId();
        response.setTopicId(topicId);
    }
}
