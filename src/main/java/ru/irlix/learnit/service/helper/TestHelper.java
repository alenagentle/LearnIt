package ru.irlix.learnit.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.entity.Test;
import ru.irlix.learnit.exception.NotFoundException;
import ru.irlix.learnit.repository.TestRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class TestHelper {

    private final TestRepository testRepository;

    public Test findTestById(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Test with id %d not found", id)));
    }

    public void checkTestExistingById(Long id) {
        boolean testExists = testRepository.existsById(id);
        if (!testExists) {
            throw new NotFoundException(String.format("Cant find test with id %d", id));
        }
    }

    public List<Test> findResultsByUsername(Long userId) {
        return testRepository.findTestsWithResultsByUserId(userId);
    }
}
