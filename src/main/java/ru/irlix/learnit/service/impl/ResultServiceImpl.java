package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.dto.request.ResultRequest;
import ru.irlix.learnit.dto.response.result.ResultResponse;
import ru.irlix.learnit.dto.response.result.FinalResultResponse;
import ru.irlix.learnit.entity.Result;
import ru.irlix.learnit.entity.Test;
import ru.irlix.learnit.mapper.ResultMapper;
import ru.irlix.learnit.repository.ResultRepository;
import ru.irlix.learnit.service.api.ResultService;
import ru.irlix.learnit.service.helper.ResultHelper;
import ru.irlix.learnit.service.helper.TestHelper;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final ResultHelper resultHelper;
    private final ResultMapper resultMapper;
    private final TestHelper testHelper;

    @Override
    @Transactional
    public ResultResponse createResult(ResultRequest resultRequest) {
        Result result = resultMapper.mapToEntity(resultRequest);
        Result savedResult = resultRepository.save(result);
        return resultMapper.mapToResponse(savedResult);
    }

    @Override
    @Transactional(readOnly = true)
    public ResultResponse findResultById(Long id) {
        Result result = resultHelper.findResultById(id);
        return resultMapper.mapToResponse(result);
    }

    @Override
    @Transactional
    public ResultResponse updateResult(Long id, ResultRequest resultRequest) {
        Result result = resultHelper.findResultById(id);
        checkAndUpdateFields(result, resultRequest);
        Result savedResult = resultRepository.save(result);
        return resultMapper.mapToResponse(savedResult);
    }

    @Override
    @Transactional
    public void deleteResult(Long id) {
        Result result = resultHelper.findResultById(id);
        resultRepository.delete(result);
    }

    @Override
    @Transactional(readOnly = true)
    public FinalResultResponse findFinalResultById(Long id) {
        Result result = resultHelper.findResultById(id);
        return resultMapper.mapToFinalResponse(result);
    }

    private void checkAndUpdateFields(Result result, ResultRequest resultRequest) {
        if (resultRequest.getTestId() != null) {
            Test test = testHelper.findTestById(resultRequest.getTestId());
            result.setTest(test);
        }
    }
}
