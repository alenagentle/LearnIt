package ru.irlix.learnit.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.entity.Test;
import ru.irlix.learnit.exception.NotFoundException;
import ru.irlix.learnit.repository.TestRepository;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class TestHelper {

    private final TestRepository testRepository;

    public Test findTestById(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Test with id " + id + " not found"));
    }
}
