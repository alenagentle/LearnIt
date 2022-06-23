package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.dto.request.TestRequest;
import ru.irlix.learnit.dto.response.test.TestFullResponse;
import ru.irlix.learnit.dto.response.test.TestResponse;
import ru.irlix.learnit.entity.Image;
import ru.irlix.learnit.entity.Test;
import ru.irlix.learnit.entity.Topic;
import ru.irlix.learnit.mapper.TestMapper;
import ru.irlix.learnit.repository.TestRepository;
import ru.irlix.learnit.service.api.TestService;
import ru.irlix.learnit.service.helper.FileHelper;
import ru.irlix.learnit.service.helper.TestHelper;
import ru.irlix.learnit.service.helper.TopicHelper;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final TestMapper testMapper;
    private final TestHelper testHelper;
    private final TopicHelper topicHelper;
    private final FileHelper fileHelper;

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
    public Page<TestResponse> findTestsByTopicId(Long topicId, Pageable pageable) {
        topicHelper.checkTopicExistingById(topicId);
        Page<Test> testList = testRepository.findTestsByTopicId(topicId, pageable);
        return testMapper.mapToResponsePage(testList);
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
    public void deleteTestById(Long id) {
        Test test = testHelper.findTestById(id);
        testRepository.delete(test);
        log.info("Test with id {} deleted", id);
        deleteImageFromS3(test);
    }

    private void checkAndUpdateFields(Test test, TestRequest request) {
        if (request.getName() != null) {
            test.setName(request.getName());
        }
        if (request.getImage() != null) {
            deleteImageFromS3(test);
            Image image = fileHelper.saveImageOnS3(request.getImage());
            test.setImage(image);
        }
        if (request.getTopicId() != null) {
            Topic topic = topicHelper.findTopicById(request.getTopicId());
            test.setTopic(topic);
        }
    }

    private void deleteImageFromS3(Test test) {
        if (test.getImage() == null) {
            return;
        }
        String key = test.getImage().getKey();
        fileHelper.deleteImageFromS3(key);
    }
}
