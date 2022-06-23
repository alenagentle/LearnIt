package ru.irlix.learnit.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ru.irlix.learnit.config.validation.annotation.NullOrNotBlank;
import ru.irlix.learnit.config.validation.group.OnCreateGroup;
import ru.irlix.learnit.config.validation.group.OnUpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TestRequest {

    @NotBlank(groups = OnCreateGroup.class, message = "{name.not-null}")
    @NullOrNotBlank(groups = OnUpdateGroup.class, message = "{name.not-blank}")
    @Size(min = 4, max = 255, message = "{name.size}")
    private String name;

    private MultipartFile image;

    @NotNull(groups = OnCreateGroup.class, message = "{topic-id.not-null}")
    @Positive(message = "{topic-id.positive}")
    private Long topicId;
}
