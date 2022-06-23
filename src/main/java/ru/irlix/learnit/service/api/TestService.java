package ru.irlix.learnit.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.irlix.learnit.dto.request.TestRequest;
import ru.irlix.learnit.dto.response.result.UsersResultResponse;
import ru.irlix.learnit.dto.response.test.TestFullResponse;
import ru.irlix.learnit.dto.response.test.TestResponse;

public interface TestService {
    TestFullResponse createTest(TestRequest testRequest);

    Page<TestResponse> findTestsByTopicId(Long topicId, Pageable pageable);

    TestFullResponse findTestById(Long id);

    TestFullResponse updateTest(Long id, TestRequest testRequest);

    void deleteTestById(Long id);
}
