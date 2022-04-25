package ru.irlix.learnit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.irlix.learnit.dto.request.TestRequest;
import ru.irlix.learnit.dto.response.test.TestFullResponse;
import ru.irlix.learnit.dto.response.test.TestResponse;
import ru.irlix.learnit.entity.Test;
import ru.irlix.learnit.entity.Topic;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.service.helper.TopicHelper;
import ru.irlix.learnit.service.helper.UserHelper;

import java.util.List;

@Mapper(componentModel = "spring", uses = QuestionMapper.class)
public abstract class TestMapper {

    @Autowired
    private TopicHelper topicHelper;

    @Autowired
    private UserHelper userHelper;

    public abstract Test mapToEntity(TestRequest testRequest);

    public abstract TestResponse mapToResponse(Test test);

    public abstract TestFullResponse mapToFullResponse(Test test);

    public abstract List<TestResponse> mapToResponseList(List<Test> tests);

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
        int questionSize = test.getQuestions().size();
        response.setQuestionsCount(questionSize);
    }

    @AfterMapping
    protected void map(@MappingTarget TestFullResponse response, Test test) {
        Long topicId = test.getTopic().getId();
        response.setTopicId(topicId);
    }
}
