package ru.irlix.learnit.dto.response.topic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicResponse {

    private Long id;

    private String name;

    private String description;

    private String image;

    private Long directionId;

    private int testCount;
}
