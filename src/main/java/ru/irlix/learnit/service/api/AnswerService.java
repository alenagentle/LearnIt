package ru.irlix.learnit.service.api;

import ru.irlix.learnit.dto.request.AnswerRequest;
import ru.irlix.learnit.dto.response.answer.AnswerFullResponse;
import ru.irlix.learnit.dto.response.answer.AnswerResponse;

import java.util.List;

public interface AnswerService {

    AnswerFullResponse createAnswer(AnswerRequest answerRequest);

    AnswerFullResponse findAnswerById(Long id);

    List<AnswerResponse> findAnswersByQuestionId(Long id);

    AnswerFullResponse updateAnswer(Long id, AnswerRequest answerRequest);

    void deleteAnswer(Long id);
}
