package ru.irlix.learnit.service.api;

import ru.irlix.learnit.dto.request.ResultRequest;
import ru.irlix.learnit.dto.response.result.ResultResponse;
import ru.irlix.learnit.dto.response.result.FinalResultResponse;

public interface ResultService {

    ResultResponse createResult(ResultRequest resultRequest);

    ResultResponse findResultById(Long id);

    ResultResponse updateResult(Long id, ResultRequest resultRequest);

    void deleteResult(Long id);

    FinalResultResponse findFinalResultById(Long id);
}
