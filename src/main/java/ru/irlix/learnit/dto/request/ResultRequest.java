package ru.irlix.learnit.dto.request;

import lombok.Getter;
import lombok.Setter;
import ru.irlix.learnit.config.validation.group.OnCreateGroup;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
public class ResultRequest {

    @NotNull(groups = OnCreateGroup.class, message = "{test-id.not-null}")
    @Positive(message = "{test-id.positive}")
    private Long testId;

    private Boolean isFinished;

    private List<@Valid AnswerRequest> answers;
}
