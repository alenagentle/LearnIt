package ru.irlix.learnit.dto.response.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.irlix.learnit.dto.response.question.QuestionResponse;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestFullResponse {

    private Long id;

    private String name;

    private String image;

    private Long topicId;

    private List<QuestionResponse> questions = new ArrayList<>();
}
