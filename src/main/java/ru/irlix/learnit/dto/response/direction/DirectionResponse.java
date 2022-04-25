package ru.irlix.learnit.dto.response.direction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirectionResponse {

    private Long id;

    private String name;

    private String image;

    private int topicsCount;
}
