package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.dto.request.AnswerRequest;
import ru.irlix.learnit.dto.response.answer.AnswerFullResponse;
import ru.irlix.learnit.dto.response.answer.AnswerResponse;
import ru.irlix.learnit.entity.Answer;
import ru.irlix.learnit.mapper.AnswerMapper;
import ru.irlix.learnit.repository.AnswerRepository;
import ru.irlix.learnit.service.api.AnswerService;
import ru.irlix.learnit.service.helper.AnswerHelper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final AnswerHelper answerHelper;

    @Override
    @Transactional
    public AnswerFullResponse createAnswer(AnswerRequest answerRequest) {
        Answer answer = answerMapper.mapToEntity(answerRequest);
        Answer savedAnswer = answerRepository.save(answer);
        return answerMapper.mapToFullResponse(savedAnswer);
    }

    @Override
    @Transactional(readOnly = true)
    public AnswerFullResponse findAnswerById(Long id) {
        Answer answer = answerHelper.findAnswerById(id);
        return answerMapper.mapToFullResponse(answer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnswerResponse> findAnswersByQuestionId(Long id) {
        List<Answer> answers = answerRepository.findAnswerByQuestionId(id);
        return answerMapper.mapToResponseList(answers);
    }

    @Override
    @Transactional
    public AnswerFullResponse updateAnswer(Long id, AnswerRequest answerRequest) {
        Answer answer = answerMapper.mapToEntity(answerRequest);
        checkAndUpdateFields(answer, answerRequest);
        return answerMapper.mapToFullResponse(answer);
    }

    @Override
    @Transactional
    public void deleteAnswer(Long id) {
        Answer answer = answerHelper.findAnswerById(id);
        answerRepository.delete(answer);
    }

    private void checkAndUpdateFields(Answer answerToUpdate, AnswerRequest answerRequest) {
        Answer answer = answerMapper.mapToEntity(answerRequest);
        if (answerRequest.getVariants() != null) {
            answerToUpdate.setVariants(answer.getVariants());
        }
        if (answerRequest.getQuestionId() != null) {
            answerToUpdate.setQuestion(answer.getQuestion());
        }
    }
}
