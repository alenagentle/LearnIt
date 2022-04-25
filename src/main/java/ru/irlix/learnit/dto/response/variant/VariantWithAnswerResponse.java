package ru.irlix.learnit.dto.response.variant;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VariantWithAnswerResponse {

    private Long id;

    private String text;

    private Boolean isSelected;

    private Boolean isRight;
}
