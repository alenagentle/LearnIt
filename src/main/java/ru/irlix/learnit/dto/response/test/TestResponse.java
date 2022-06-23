package ru.irlix.learnit.dto.response.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResponse {

    private Long id;

    private String name;

    private String image;

    private Long topicId;

    private Long authorId;

    private int questionsCount;

    private int resultCount;

    private int questionsAnsweredCount;

    private int progress;
}
