package ru.irlix.learnit.dto.request;

import lombok.Getter;
import lombok.Setter;
import ru.irlix.learnit.config.validation.group.OnCreateGroup;
import ru.irlix.learnit.config.validation.group.OnUpdateGroup;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
public class AnswerFullRequest {

    @NotNull(groups = OnCreateGroup.class, message = "{question-id.not-null}")
    @Positive(message = "{question-id.positive}")
    private Long questionId;

    @NotNull(groups = OnUpdateGroup.class, message = "{result-id.not-null}")
    @Positive(message = "{result-id.positive}")
    private Long resultId;

    private List<@Positive(message = "{variant-id.positive}") Long> variants;
}
