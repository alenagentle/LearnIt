package ru.irlix.learnit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.irlix.learnit.dto.request.DirectionRequest;
import ru.irlix.learnit.dto.response.direction.DirectionFullResponse;
import ru.irlix.learnit.dto.response.direction.DirectionResponse;
import ru.irlix.learnit.entity.Direction;

import java.util.List;

@Mapper(componentModel = "spring", uses = TopicMapper.class)
public abstract class DirectionMapper {

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
    }
}
