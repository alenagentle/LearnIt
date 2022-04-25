package ru.irlix.learnit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.irlix.learnit.dto.request.TopicRequest;
import ru.irlix.learnit.dto.response.topic.TopicFullResponse;
import ru.irlix.learnit.dto.response.topic.TopicResponse;
import ru.irlix.learnit.entity.Topic;
import ru.irlix.learnit.entity.Direction;
import ru.irlix.learnit.exception.NoRelatedDirectionException;
import ru.irlix.learnit.service.helper.DirectionHelper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TestMapper.class)
public abstract class TopicMapper {

    @Autowired
    private DirectionHelper directionHelper;

    public abstract Topic mapToEntity(TopicRequest request);

    public abstract TopicFullResponse mapToFullResponse(Topic topic);

    public abstract List<TopicResponse> mapToResponseList(List<Topic> topic);

    public Page<TopicResponse> mapToResponsePage(Page<Topic> topics) {
        List<TopicResponse> content = mapToResponseList(topics.getContent());
        return new PageImpl<>(content, topics.getPageable(), topics.getTotalElements());
    }

    @AfterMapping
    protected void map(@MappingTarget Topic topic, TopicRequest request) {
        List<Direction> directions = directionHelper.findDirectionsByIds(request.getDirectionIdSet());
        if (directions.isEmpty()) {
            throw new NoRelatedDirectionException();
        }
        topic.setDirections(directions);
    }

    @AfterMapping
    protected void map(@MappingTarget TopicResponse response, Topic topic) {
        int testsCount = topic.getTests().size();
        response.setTestCount(testsCount);
    }
}
