package ru.irlix.learnit.dto.response.result;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ResultResponse {

    private Long id;

    private Instant lastUpdate;

    private Long testId;

    private Long userId;

    private Integer answersCount;

    private Boolean isFinished;
}
