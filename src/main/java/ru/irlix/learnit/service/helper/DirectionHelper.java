package ru.irlix.learnit.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.entity.Direction;
import ru.irlix.learnit.exception.NotFoundException;
import ru.irlix.learnit.repository.DirectionRepository;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class DirectionHelper {

    private final DirectionRepository directionRepository;

    public Direction findDirectionById(Long id) {
        return directionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Cant find direction with id %d", id)));
    }

    public void checkDirectionExistingById(Long id) {
        boolean directionExists = directionRepository.existsById(id);
        if (!directionExists) {
            throw new NotFoundException(String.format("Cant find direction with id %d", id));
        }
    }

    public List<Direction> findDirectionsByIds(Collection<Long> idCollection) {
        return directionRepository.findDirectionByIdIn(idCollection);
    }
}
