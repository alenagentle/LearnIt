package ru.irlix.learnit.dto.response.answer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NestedAnswer {

    private Long id;

    private Long questionId;

    private List<Long> selectedVariants;
}
