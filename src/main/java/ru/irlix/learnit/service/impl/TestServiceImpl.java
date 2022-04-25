package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.dto.request.TestRequest;
import ru.irlix.learnit.dto.response.test.TestFullResponse;
import ru.irlix.learnit.dto.response.test.TestResponse;
import ru.irlix.learnit.entity.Test;
import ru.irlix.learnit.mapper.TestMapper;
import ru.irlix.learnit.repository.TestRepository;
import ru.irlix.learnit.service.api.TestService;
import ru.irlix.learnit.service.helper.TestHelper;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final TestMapper testMapper;
    private final TestHelper testHelper;

    @Override
    @Transactional
    public TestFullResponse createTest(TestRequest testRequest) {
        Test test = testMapper.mapToEntity(testRequest);
        Test savedTest = testRepository.save(test);
        log.info("New test saved. Id = {}", savedTest.getId());
        return testMapper.mapToFullResponse(savedTest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestResponse> findAllTests() {
        List<Test> testList = testRepository.findAll();
        return testMapper.mapToResponseList(testList);
    }

    @Override
    @Transactional(readOnly = true)
    public TestFullResponse findTestById(Long id) {
        Test test = testHelper.findTestById(id);
        return testMapper.mapToFullResponse(test);
    }

    @Override
    @Transactional
    public TestFullResponse updateTest(Long id, TestRequest testRequest) {
        Test test = testHelper.findTestById(id);
        checkAndUpdateFields(test, testRequest);
        Test updatedTest = testRepository.save(test);
        log.info("Test with id {} updated", id);
        return testMapper.mapToFullResponse(updatedTest);
    }

    @Override
    @Transactional
    public void deleteTest(Long id) {
        Test test = testHelper.findTestById(id);
        testRepository.delete(test);
        log.info("Test with id {} deleted", id);
    }

    private void checkAndUpdateFields(Test test, TestRequest testRequest) {
        if (testRequest.getName() != null) {
            test.setName(testRequest.getName());
        }
        if (testRequest.getImage() != null) {
            test.setImage(testRequest.getImage());
        }
    }
}
