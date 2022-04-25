package ru.irlix.learnit.dto.response.answer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import ru.irlix.learnit.dto.response.question.NestedQuestion;
import ru.irlix.learnit.dto.response.variant.VariantWithAnswerResponse;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinalAnswerResponse {

    private Long id;

    private NestedQuestion question;

    private List<VariantWithAnswerResponse> variants;
}
