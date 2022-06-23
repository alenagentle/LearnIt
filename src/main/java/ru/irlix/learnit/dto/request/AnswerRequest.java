package ru.irlix.learnit.dto.request;

import lombok.Getter;
import lombok.Setter;
import ru.irlix.learnit.config.validation.group.OnCreateGroup;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
public class AnswerRequest {
    @NotNull(groups = OnCreateGroup.class, message = "{question-id.not-null}")
    @Positive(message = "{question-id.positive}")
    private Long questionId;

    private List<@Positive(message = "{variant-id.positive}") Long> variants;
}
