package ru.irlix.learnit.dto.response.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponse {

    private Long id;

    private Long questionId;

    private Long resultId;

    private int variantsCount;
}
