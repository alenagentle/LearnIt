package ru.irlix.learnit.dto.response.result;

import lombok.Getter;
import lombok.Setter;
import ru.irlix.learnit.dto.response.test.TestWithResultResponse;

import java.util.List;

@Getter
@Setter
public class UsersResultResponse {

    private List<TestWithResultResponse> tests;
}
