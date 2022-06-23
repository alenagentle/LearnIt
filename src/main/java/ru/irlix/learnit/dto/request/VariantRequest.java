package ru.irlix.learnit.dto.request;

import lombok.Getter;
import lombok.Setter;
import ru.irlix.learnit.config.validation.annotation.NullOrNotBlank;
import ru.irlix.learnit.config.validation.group.OnCreateGroup;
import ru.irlix.learnit.config.validation.group.OnUpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
public class VariantRequest {

    @NotBlank(groups = OnCreateGroup.class, message = "{text.not-null}")
    @NullOrNotBlank(groups = OnUpdateGroup.class, message = "{text.not-blank}")
    @Size(max = 255, message = "{text.size.variant}")
    private String text;

    private Boolean isRight;

    @NotNull(groups = OnCreateGroup.class, message = "{question-id.not-null}")
    @Positive(message = "{question-id.positive}")
    private Long questionId;
}