package ru.irlix.learnit.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {

    private String text;

    private String description;

    private String image;

    private Long testId;

    private List<NestedVariant> variants;
}
