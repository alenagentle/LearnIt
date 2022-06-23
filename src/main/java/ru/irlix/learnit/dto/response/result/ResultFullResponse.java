package ru.irlix.learnit.dto.response.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.irlix.learnit.dto.response.answer.NestedAnswerResponse;
import ru.irlix.learnit.dto.response.user.UserResponse;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultFullResponse {

    private Long id;

    private Instant lastUpdate;

    private UserResponse user;

    private Long testId;

    private List<NestedAnswerResponse> answers;

    private Boolean isFinished;
}
