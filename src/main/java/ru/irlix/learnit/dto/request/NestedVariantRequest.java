package ru.irlix.learnit.dto.request;

import lombok.Getter;
import lombok.Setter;
import ru.irlix.learnit.config.validation.group.OnUpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class NestedVariantRequest {

    @NotNull(groups = OnUpdateGroup.class, message = "{variant-id.not-null}")
    private Long id;

    @NotBlank(message = "{text.not-null}")
    @Size(max = 255, message = "{text.size.variant}")
    private String text;

    private Boolean isRight;
}
