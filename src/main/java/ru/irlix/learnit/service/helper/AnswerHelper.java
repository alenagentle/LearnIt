package ru.irlix.learnit.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.entity.Answer;
import ru.irlix.learnit.exception.NotFoundException;
import ru.irlix.learnit.repository.AnswerRepository;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class AnswerHelper {

    private final AnswerRepository answerRepository;

    public Answer findAnswerById(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Answer with id %d not found", id)));
    }
}
