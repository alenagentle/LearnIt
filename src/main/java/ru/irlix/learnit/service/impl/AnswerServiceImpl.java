package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.dto.request.AnswerFullRequest;
import ru.irlix.learnit.dto.response.answer.AnswerFullResponse;
import ru.irlix.learnit.dto.response.answer.AnswerResponse;
import ru.irlix.learnit.entity.Answer;
import ru.irlix.learnit.mapper.AnswerMapper;
import ru.irlix.learnit.repository.AnswerRepository;
import ru.irlix.learnit.service.api.AnswerService;
import ru.irlix.learnit.service.helper.AnswerHelper;
import ru.irlix.learnit.service.helper.QuestionHelper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final AnswerHelper answerHelper;
    private final QuestionHelper questionHelper;

    @Override
    @Transactional
    public AnswerFullResponse createAnswer(AnswerFullRequest answerFullRequest) {
        Answer answer = answerMapper.mapToEntity(answerFullRequest);
        Answer savedAnswer = answerRepository.save(answer);
        return answerMapper.mapToFullResponse(savedAnswer);
    }

    @Override
    @Transactional
    public List<AnswerResponse> createAnswers(List<AnswerFullRequest> answerFullRequests) {
        List<Answer> answers = answerMapper.mapToEntityList(answerFullRequests);
        List<Answer> savedAnswers = answerRepository.saveAll(answers);
        return answerMapper.mapToResponseList(savedAnswers);
    }

    @Override
    @Transactional(readOnly = true)
    public AnswerFullResponse findAnswerById(Long id) {
        Answer answer = answerHelper.findAnswerById(id);
        return answerMapper.mapToFullResponse(answer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnswerResponse> findAnswersByQuestionId(Long questionId) {
        questionHelper.checkQuestionExistingById(questionId);
        List<Answer> answers = answerRepository.findAnswerByQuestionId(questionId);
        return answerMapper.mapToResponseList(answers);
    }

    @Override
    @Transactional
    public AnswerFullResponse updateAnswer(Long id, AnswerFullRequest answerFullRequest) {
        Answer answer = answerHelper.findAnswerById(id);
        checkAndUpdateFields(answer, answerFullRequest);
        return answerMapper.mapToFullResponse(answer);
    }

    @Override
    @Transactional
    public void deleteAnswer(Long id) {
        Answer answer = answerHelper.findAnswerById(id);
        answerRepository.delete(answer);
    }

    private void checkAndUpdateFields(Answer answerToUpdate, AnswerFullRequest request) {
        Answer answer = answerMapper.mapToEntity(request);
        if (request.getVariants() != null) {
            answerToUpdate.setVariants(answer.getVariants());
        }
        if (request.getQuestionId() != null) {
            answerToUpdate.setQuestion(answer.getQuestion());
        }
    }
}
