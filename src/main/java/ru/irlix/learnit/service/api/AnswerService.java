package ru.irlix.learnit.service.api;

import ru.irlix.learnit.dto.request.AnswerFullRequest;
import ru.irlix.learnit.dto.response.answer.AnswerFullResponse;
import ru.irlix.learnit.dto.response.answer.AnswerResponse;

import java.util.List;

public interface AnswerService {

    AnswerFullResponse createAnswer(AnswerFullRequest answerFullRequest);

    List<AnswerResponse> createAnswers(List<AnswerFullRequest> answerFullRequests);

    AnswerFullResponse findAnswerById(Long id);

    List<AnswerResponse> findAnswersByQuestionId(Long id);

    AnswerFullResponse updateAnswer(Long id, AnswerFullRequest answerFullRequest);

    void deleteAnswer(Long id);
}
