package ru.irlix.learnit.dto.response.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.irlix.learnit.dto.response.variant.VariantResponse;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerFullResponse {

    private Long id;

    private Long questionId;

    private Long resultId;

    private List<VariantResponse> variants;
}
