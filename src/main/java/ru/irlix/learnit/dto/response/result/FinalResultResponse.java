package ru.irlix.learnit.dto.response.result;

import lombok.Getter;
import lombok.Setter;
import ru.irlix.learnit.dto.response.answer.FinalAnswerResponse;
import ru.irlix.learnit.dto.response.test.NestedTestResponse;
import ru.irlix.learnit.dto.response.user.UserResponse;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class FinalResultResponse {

    private Long id;

    private Instant lastUpdate;

    private NestedTestResponse test;

    private UserResponse user;

    private List<FinalAnswerResponse> answers;

    private Boolean isFinished;
}
