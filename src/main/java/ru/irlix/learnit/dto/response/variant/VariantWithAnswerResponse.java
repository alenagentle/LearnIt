package ru.irlix.learnit.dto.response.variant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariantWithAnswerResponse {

    private Long id;

    private String text;

    private Boolean isSelected;

    private Boolean isRight;
}
