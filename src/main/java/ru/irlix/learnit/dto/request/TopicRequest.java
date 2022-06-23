package ru.irlix.learnit.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ru.irlix.learnit.config.validation.annotation.NullOrNotBlank;
import ru.irlix.learnit.config.validation.group.OnCreateGroup;
import ru.irlix.learnit.config.validation.group.OnUpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class TopicRequest {

    @NotBlank(groups = OnCreateGroup.class, message = "{name.not-null}")
    @NullOrNotBlank(groups = OnUpdateGroup.class, message = "{name.not-blank}")
    @Size(min = 4, max = 255, message = "{name.size}")
    private String name;

    @Size(max = 512, message = "{description.size}")
    private String description;

    private MultipartFile image;

    @Size(max = 2048, message = "{wiki.size}")
    private String wikiText;

    private List<String> links;

    private Set<@Positive(message = "{direction-id.positive}") Long> directionIdSet;
}
