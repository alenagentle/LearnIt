package ru.irlix.learnit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.irlix.learnit.dto.request.DirectionRequest;
import ru.irlix.learnit.dto.response.direction.DirectionFullResponse;
import ru.irlix.learnit.dto.response.direction.DirectionResponse;
import ru.irlix.learnit.service.api.DirectionService;

@RestController
@RequestMapping("/api/direction")
@RequiredArgsConstructor
public class DirectionController {

    private final DirectionService directionService;

    @PostMapping
    public DirectionFullResponse createDirection(@RequestBody DirectionRequest request) {
        return directionService.createDirection(request);
    }

    @PutMapping("/{id}")
    public DirectionFullResponse updateDirection(@PathVariable Long id, @RequestBody DirectionRequest request) {
        return directionService.updateDirection(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteDirection(@PathVariable Long id) {
        directionService.deleteDirection(id);
    }

    @GetMapping
    public Page<DirectionResponse> findAllDirections(Pageable pageable) {
        return directionService.findAllDirections(pageable);
    }

    @GetMapping("/{id}")
    public DirectionFullResponse findDirectionById(@PathVariable Long id) {
        return directionService.findDirectionById(id);
    }
}
