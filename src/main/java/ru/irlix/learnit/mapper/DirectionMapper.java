package ru.irlix.learnit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.irlix.learnit.dto.request.DirectionRequest;
import ru.irlix.learnit.dto.response.direction.DirectionFullResponse;
import ru.irlix.learnit.dto.response.direction.DirectionResponse;
import ru.irlix.learnit.entity.Direction;
import ru.irlix.learnit.entity.Result;
import ru.irlix.learnit.entity.Test;
import ru.irlix.learnit.entity.Topic;
import ru.irlix.learnit.service.helper.UserHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", uses = {TopicMapper.class, ImageMapper.class})
public abstract class DirectionMapper {

    @Autowired
    private UserHelper userHelper;

    public abstract Direction mapToEntity(DirectionRequest request);

    public abstract DirectionFullResponse mapToFullResponse(Direction direction);

    public abstract List<DirectionResponse> mapToResponseList(List<Direction> directions);

    public Page<DirectionResponse> mapToResponsePage(Page<Direction> directions) {
        List<DirectionResponse> content = mapToResponseList(directions.getContent());
        return new PageImpl<>(content, directions.getPageable(), directions.getTotalElements());
    }

    @AfterMapping
    protected void map(@MappingTarget DirectionResponse response, Direction direction) {
        int topicsCount = direction.getTopics().size();
        response.setTopicsCount(topicsCount);
        response.setProgress(getProgress(direction));
    }

    private long getProgress(Direction direction) {
        int finishedCount = 0;
        for (Topic topic : direction.getTopics()) {
            for (Test test : topic.getTests()) {
                Map<Long, Integer> map = new HashMap<>();
                for (Result result : test.getResults()) {
                    if (result.getIsFinished() && result.getUser().equals(userHelper.getCurrentUserData())) {
                        if (!map.containsKey(result.getTest().getId()))
                            map.put(result.getTest().getId(), ++finishedCount);
                    }
                }
            }
        }
        int testCount = direction.getTopics().stream()
                .mapToInt(topic -> topic.getTests().size())
                .sum();
        if (testCount <= 0) {
            testCount = 1;
        }
        return Math.round((double) finishedCount / testCount * 100);
    }
}
