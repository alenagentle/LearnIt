package ru.irlix.learnit.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.entity.Result;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.exception.NotFoundException;
import ru.irlix.learnit.repository.ResultRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class ResultHelper {

    private final ResultRepository resultRepository;

    public Result findResultById(Long id) {
        return resultRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Result with id %d not found", id)));
    }

    public List<Result> findRecentResult(UserData user) {
        return resultRepository.findRecentResult(user);
    }
}
