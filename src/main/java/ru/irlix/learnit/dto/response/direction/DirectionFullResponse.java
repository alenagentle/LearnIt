package ru.irlix.learnit.dto.response.direction;

import lombok.Getter;
import lombok.Setter;
import ru.irlix.learnit.dto.response.topic.TopicResponse;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DirectionFullResponse {

    private Long id;

    private String name;

    private String image;

    private List<TopicResponse> topics = new ArrayList<>();
}
