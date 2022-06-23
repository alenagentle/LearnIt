package ru.irlix.learnit.dto.response.result;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class NestedResultResponse {

    private Long id;

    private Instant lastUpdate;

    private Integer answersCount;

    private Boolean isFinished;
}
