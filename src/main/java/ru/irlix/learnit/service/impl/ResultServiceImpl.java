package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.dto.request.ResultRequest;
import ru.irlix.learnit.dto.response.answer.FinalAnswerResponse;
import ru.irlix.learnit.dto.response.result.FinalResultResponse;
import ru.irlix.learnit.dto.response.result.ReportResponse;
import ru.irlix.learnit.dto.response.result.ResultFullResponse;
import ru.irlix.learnit.dto.response.result.ResultResponse;
import ru.irlix.learnit.dto.response.result.UsersResultResponse;
import ru.irlix.learnit.entity.Answer;
import ru.irlix.learnit.entity.Question;
import ru.irlix.learnit.entity.Result;
import ru.irlix.learnit.entity.Test;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.mapper.AnswerMapper;
import ru.irlix.learnit.mapper.QuestionMapper;
import ru.irlix.learnit.mapper.ResultMapper;
import ru.irlix.learnit.report.ReportHelper;
import ru.irlix.learnit.repository.ResultRepository;
import ru.irlix.learnit.security.util.SecurityUtil;
import ru.irlix.learnit.service.api.ResultService;
import ru.irlix.learnit.service.helper.ResultHelper;
import ru.irlix.learnit.service.helper.TestHelper;
import ru.irlix.learnit.service.helper.UserHelper;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final ResultHelper resultHelper;
    private final TestHelper testHelper;
    private final ResultMapper resultMapper;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;
    private final UserHelper userHelper;
    private final ReportHelper reportHelper;

    @Override
    @Transactional
    public ResultFullResponse createResult(ResultRequest resultRequest) {
        Result result = resultMapper.mapToEntity(resultRequest);
        Result savedResult = resultRepository.save(result);
        return resultMapper.mapToFullResponse(savedResult);
    }

    @Override
    @Transactional(readOnly = true)
    public ResultFullResponse findResultById(Long id) {
        Result result = resultHelper.findResultById(id);
        return resultMapper.mapToFullResponse(result);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultResponse> findResultByTestId(Long id) {
        testHelper.findTestById(id);
        Long currentUserId = userHelper.getCurrentUserData().getId();
        List<Result> results = resultRepository.findResultsByTestIdAndUserId(id, currentUserId);
        return resultMapper.mapToResponseList(results);
    }

    @Override
    @Transactional
    public ResultFullResponse updateResult(Long id, ResultRequest resultRequest) {
        Result result = resultHelper.findResultById(id);
        checkAndUpdateFields(result, resultRequest);
        Result savedResult = resultRepository.save(result);
        return resultMapper.mapToFullResponse(savedResult);
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
        List<Question> questions = result.getTest().getQuestions();
        List<Answer> answers = result.getAnswers();

        List<FinalAnswerResponse> nestedQuestions = questions.stream()
                .map(question -> mapToFinalResponse(answers, question))
                .toList();
        return resultMapper.mapToFinalResponse(result, nestedQuestions);
    }

    @Override
    @Transactional(readOnly = true)
    public UsersResultResponse findResultsByUsername(String username) {
        UserData user = userHelper.findUserByUsername(username);
        checkAccessToResults(user);
        List<Test> resultsInTestsByUsername = testHelper.findResultsByUsername(user.getId());
        return resultMapper.mapToUsersResultResponse(resultsInTestsByUsername);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportResponse getReport(Long id) {
        Result result = resultHelper.findResultById(id);
        return reportHelper.getReport(result);
    }

    private void checkAccessToResults(UserData user) {
        if (!SecurityUtil.hasAdminRoles() && !user.equals(userHelper.getCurrentUserData())) {
            throw new AccessDeniedException("Cant access to other users results");
        }
    }

    private FinalAnswerResponse mapToFinalResponse(List<Answer> answers, Question q) {
        return answers.stream().filter(a -> Objects.equals(a.getQuestion().getId(), q.getId()))
                .findAny()
                .map(answerMapper::mapToFinalResponse)
                .orElse(questionMapper.mapToFinalResponse(q));
    }

    private void checkAndUpdateFields(Result result, ResultRequest request) {
        if (request.getTestId() != null) {
            Test test = testHelper.findTestById(request.getTestId());
            result.setTest(test);
        }
        if (request.getIsFinished() != null) {
            result.setIsFinished(request.getIsFinished());
        }
    }
}
