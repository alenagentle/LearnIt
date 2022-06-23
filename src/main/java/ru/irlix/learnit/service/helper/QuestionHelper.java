package ru.irlix.learnit.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.entity.Question;
import ru.irlix.learnit.exception.NotFoundException;
import ru.irlix.learnit.repository.QuestionRepository;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class QuestionHelper {

    private final QuestionRepository questionRepository;

    public Question findQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Question with id %d not found", id)));
    }

    public void checkQuestionExistingById(Long id) {
        boolean questionExists = questionRepository.existsById(id);
        if (!questionExists) {
            throw new NotFoundException(String.format("Cant find question with id %d", id));
        }
    }
}