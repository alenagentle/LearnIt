package ru.irlix.learnit.dto.response.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.irlix.learnit.dto.response.variant.VariantResponse;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {

    private Long id;

    private String text;

    private String description;

    private String image;

    private Long testId;

    private List<VariantResponse> variants = new ArrayList<>();
}
