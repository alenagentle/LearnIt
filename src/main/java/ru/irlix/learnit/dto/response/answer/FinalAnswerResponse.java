package ru.irlix.learnit.dto.response.answer;

import lombok.Getter;
import lombok.Setter;
import ru.irlix.learnit.dto.response.question.NestedQuestion;
import ru.irlix.learnit.dto.response.variant.VariantWithAnswerResponse;

import java.util.List;

@Getter
@Setter
public class FinalAnswerResponse {

    private Long id;

    private NestedQuestion question;

    private List<VariantWithAnswerResponse> variants;
}
