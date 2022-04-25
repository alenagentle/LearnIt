package ru.irlix.learnit.dto.response.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.irlix.learnit.dto.response.answer.NestedAnswer;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponse {

    private Long id;

    private Instant lastUpdate;

    private Long testId;

    private List<NestedAnswer> answers;
}
