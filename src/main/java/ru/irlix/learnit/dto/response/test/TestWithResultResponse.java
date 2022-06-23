package ru.irlix.learnit.dto.response.test;

import lombok.Getter;
import lombok.Setter;
import ru.irlix.learnit.dto.response.result.NestedResultResponse;

import java.util.List;

@Getter
@Setter
public class TestWithResultResponse {

    private Long id;

    private String name;

    private List<NestedResultResponse> results;
}
