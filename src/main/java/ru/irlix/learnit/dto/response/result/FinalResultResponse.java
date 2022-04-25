package ru.irlix.learnit.dto.response.result;

import lombok.Getter;
import lombok.Setter;
import ru.irlix.learnit.dto.response.test.TestResponse;
import ru.irlix.learnit.dto.response.answer.FinalAnswerResponse;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class FinalResultResponse {

    private Long id;

    private Instant lastUpdate;

    private TestResponse test;

    private List<FinalAnswerResponse> answers;
}
