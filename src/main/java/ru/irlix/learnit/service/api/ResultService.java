package ru.irlix.learnit.service.api;

import ru.irlix.learnit.dto.request.ResultRequest;
import ru.irlix.learnit.dto.response.result.FinalResultResponse;
import ru.irlix.learnit.dto.response.result.ReportResponse;
import ru.irlix.learnit.dto.response.result.ResultFullResponse;
import ru.irlix.learnit.dto.response.result.ResultResponse;
import ru.irlix.learnit.dto.response.result.UsersResultResponse;

import java.util.List;

public interface ResultService {

    ResultFullResponse createResult(ResultRequest resultRequest);

    ResultFullResponse findResultById(Long id);

    List<ResultResponse> findResultByTestId(Long id);

    ResultFullResponse updateResult(Long id, ResultRequest resultRequest);

    void deleteResult(Long id);

    FinalResultResponse findFinalResultById(Long id);

    UsersResultResponse findResultsByUsername(String username);

    ReportResponse getReport(Long id);
}
