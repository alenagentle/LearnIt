package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.dto.request.DirectionRequest;
import ru.irlix.learnit.dto.response.direction.DirectionFullResponse;
import ru.irlix.learnit.dto.response.direction.DirectionResponse;
import ru.irlix.learnit.entity.Direction;
import ru.irlix.learnit.exception.NameAlreadyTakenException;
import ru.irlix.learnit.mapper.DirectionMapper;
import ru.irlix.learnit.repository.DirectionRepository;
import ru.irlix.learnit.service.api.DirectionService;
import ru.irlix.learnit.service.helper.DirectionHelper;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionServiceImpl implements DirectionService {

    private final DirectionRepository directionRepository;
    private final DirectionHelper directionHelper;
    private final DirectionMapper directionMapper;

    @Override
    @Transactional
    public DirectionFullResponse createDirection(DirectionRequest request) {
        validateName(request);
        Direction direction = directionMapper.mapToEntity(request);
        Direction savedDirection = directionRepository.save(direction);
        log.info("New direction saved. Id = {}", savedDirection.getId());
        return directionMapper.mapToFullResponse(savedDirection);
    }

    @Override
    @Transactional
    public DirectionFullResponse updateDirection(Long id, DirectionRequest request) {
        Direction direction = directionHelper.findDirectionById(id);
        validateName(request, direction);
        checkAndUpdateFields(request, direction);
        Direction savedDirection = directionRepository.save(direction);
        log.info("Direction with id {} updated", id);
        return directionMapper.mapToFullResponse(savedDirection);
    }

    @Override
    @Transactional
    public void deleteDirection(Long id) {
        Direction direction = directionHelper.findDirectionById(id);
        directionRepository.delete(direction);
        log.info("Direction with id {} deleted", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DirectionResponse> findAllDirections(Pageable pageable) {
        Page<Direction> directions = directionRepository.findAll(pageable);
        return directionMapper.mapToResponsePage(directions);
    }

    @Override
    @Transactional(readOnly = true)
    public DirectionFullResponse findDirectionById(Long id) {
        Direction direction = directionHelper.findDirectionById(id);
        return directionMapper.mapToFullResponse(direction);
    }

    private void checkAndUpdateFields(DirectionRequest request, Direction direction) {
        if (request.getName() != null) {
            direction.setName(request.getName());
        }
        if (request.getImage() != null) {
            direction.setImage(request.getImage());
        }
    }

    private void validateName(DirectionRequest request) {
        String name = request.getName();
        boolean existsByName = directionRepository.existsByName(name);
        if (existsByName) {
            throw new NameAlreadyTakenException(name);
        }
    }

    private void validateName(DirectionRequest request, Direction direction) {
        if (!Objects.equals(request.getName(), direction.getName())) {
            validateName(request);
        }
    }
}
