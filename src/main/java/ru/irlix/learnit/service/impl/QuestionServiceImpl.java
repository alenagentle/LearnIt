package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.dto.request.QuestionRequest;
import ru.irlix.learnit.dto.response.question.QuestionResponse;
import ru.irlix.learnit.entity.Question;
import ru.irlix.learnit.entity.Test;
import ru.irlix.learnit.mapper.QuestionMapper;
import ru.irlix.learnit.repository.QuestionRepository;
import ru.irlix.learnit.service.api.QuestionService;
import ru.irlix.learnit.service.helper.QuestionHelper;
import ru.irlix.learnit.service.helper.TestHelper;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final QuestionHelper questionHelper;
    private final TestHelper testHelper;

    @Override
    @Transactional
    public QuestionResponse createQuestion(QuestionRequest questionRequest) {
        Question question = questionMapper.mapToEntity(questionRequest);
        Question savedQuestion = questionRepository.save(question);
        log.info("New question saved. Id = {}", savedQuestion.getId());
        return questionMapper.mapToResponse(savedQuestion);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionResponse findQuestionById(Long id) {
        Question question = questionHelper.findQuestionById(id);
        return questionMapper.mapToResponse(question);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionResponse> findQuestionsByTestId(Long id) {
        List<Question> questions = questionRepository.findQuestionsByTestId(id);
        return questionMapper.mapToResponseList(questions);
    }

    @Override
    @Transactional
    public QuestionResponse updateQuestion(Long id, QuestionRequest questionRequest) {
        Question question = questionHelper.findQuestionById(id);
        checkAndUpdateFields(question, questionRequest);
        Question savedQuestion = questionRepository.save(question);
        log.info("Question with id {} updated", id);
        return questionMapper.mapToResponse(savedQuestion);
    }

    @Override
    @Transactional
    public void deleteQuestion(Long id) {
        Question question = questionHelper.findQuestionById(id);
        questionRepository.delete(question);
        log.info("Question with id {} deleted", id);
    }

    private void checkAndUpdateFields(Question question, QuestionRequest questionRequest) {
        if (questionRequest.getText() != null) {
            question.setText(questionRequest.getText());
        }
        if (questionRequest.getDescription() != null) {
            question.setDescription(questionRequest.getDescription());
        }
        if (questionRequest.getImage() != null) {
            question.setImage(questionRequest.getImage());
        }
        if (questionRequest.getTestId() != null) {
            Test test = testHelper.findTestById(questionRequest.getTestId());
            question.setTest(test);
        }
    }
}
