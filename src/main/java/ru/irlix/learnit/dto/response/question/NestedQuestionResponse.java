package ru.irlix.learnit.dto.response.question;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NestedQuestionResponse {

    private Long id;

    private String text;

    private String description;

    private String image;
}
