package ru.irlix.learnit.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.irlix.learnit.dto.request.DirectionRequest;
import ru.irlix.learnit.dto.response.direction.DirectionFullResponse;
import ru.irlix.learnit.dto.response.direction.DirectionResponse;

import java.util.List;

public interface DirectionService {

    DirectionFullResponse createDirection(DirectionRequest request);

    DirectionFullResponse updateDirection(Long id, DirectionRequest request);

    void deleteDirectionById(Long id);

    Page<DirectionResponse> findAllDirections(Pageable pageable);

    DirectionFullResponse findDirectionById(Long id);

    List<DirectionResponse> findRecentDirections();
}
