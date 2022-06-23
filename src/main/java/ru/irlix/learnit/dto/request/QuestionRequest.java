package ru.irlix.learnit.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ru.irlix.learnit.config.validation.annotation.NullOrNotBlank;
import ru.irlix.learnit.config.validation.group.OnCreateGroup;
import ru.irlix.learnit.config.validation.group.OnUpdateGroup;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class QuestionRequest {

    @NotBlank(groups = OnCreateGroup.class, message = "{text.not-null}")
    @NullOrNotBlank(groups = OnUpdateGroup.class, message = "{text.not-blank}")
    @Size(min = 4, max = 512, message = "{text.size}")
    private String text;

    @Size(max = 512, message = "{description.size}")
    private String description;

    private MultipartFile image;

    @NotNull(groups = OnCreateGroup.class, message = "{test-id.not-null}")
    @Positive(message = "{test-id.positive}")
    private Long testId;

    @Size(max = 6, message = "{variants.limit}")
    private List<@Valid NestedVariantRequest> variants;
}
