package ru.irlix.learnit.dto.response.topic;

import lombok.Getter;
import lombok.Setter;
import ru.irlix.learnit.dto.response.test.TestResponse;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TopicFullResponse {

    private Long id;

    private String name;

    private String description;

    private String image;

    private List<TestResponse> tests = new ArrayList<>();
}
