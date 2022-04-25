package ru.irlix.learnit.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VariantRequest {

    private String text;

    private Boolean isRight;

    private Long questionId;
}