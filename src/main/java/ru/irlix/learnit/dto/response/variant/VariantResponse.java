package ru.irlix.learnit.dto.response.variant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VariantResponse {

    private Long id;

    private String text;

    private Boolean isRight;

    private Long questionId;
}