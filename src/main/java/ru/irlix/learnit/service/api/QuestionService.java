package ru.irlix.learnit.service.api;

import ru.irlix.learnit.dto.request.QuestionRequest;
import ru.irlix.learnit.dto.response.question.QuestionResponse;

import java.util.List;

public interface QuestionService {
    QuestionResponse createQuestion(QuestionRequest questionRequest);

    QuestionResponse findQuestionById(Long id);

    List<QuestionResponse> findQuestionsByTestId(Long id);

    QuestionResponse updateQuestion(Long id, QuestionRequest questionRequest);

    void deleteQuestion(Long id);
}
