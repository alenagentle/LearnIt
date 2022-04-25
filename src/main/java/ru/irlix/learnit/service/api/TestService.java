package ru.irlix.learnit.service.api;

import ru.irlix.learnit.dto.request.TestRequest;
import ru.irlix.learnit.dto.response.test.TestFullResponse;
import ru.irlix.learnit.dto.response.test.TestResponse;

import java.util.List;

public interface TestService {
    TestFullResponse createTest(TestRequest testRequest);

    List<TestResponse> findAllTests();

    TestFullResponse findTestById(Long id);

    TestFullResponse updateTest(Long id, TestRequest testRequest);

    void deleteTest(Long id);
}
