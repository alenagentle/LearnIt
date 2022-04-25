package ru.irlix.learnit.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TopicRequest {

    private String name;

    private String description;

    private String image;

    private Set<Long> directionIdSet;
}
