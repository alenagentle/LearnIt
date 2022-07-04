package ru.irlix.learnit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.irlix.learnit.config.validation.group.OnCreateGroup;
import ru.irlix.learnit.config.validation.group.OnUpdateGroup;
import ru.irlix.learnit.dto.request.DirectionRequest;
import ru.irlix.learnit.dto.response.direction.DirectionFullResponse;
import ru.irlix.learnit.dto.response.direction.DirectionResponse;
import ru.irlix.learnit.service.api.DirectionService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/direction")
@RequiredArgsConstructor
@Validated
@CrossOrigin
public class DirectionController {

    private final DirectionService directionService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Validated(OnCreateGroup.class)
    public DirectionFullResponse createDirection(@Valid DirectionRequest request) {
        return directionService.createDirection(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Validated(OnUpdateGroup.class)
    public DirectionFullResponse updateDirection(@PathVariable
                                                 @Positive(groups = OnUpdateGroup.class,
                                                         message = "{id.positive}") Long id,
                                                 @Valid DirectionRequest request) {
        return directionService.updateDirection(id, request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @DeleteMapping("/{id}")
    public void deleteDirection(@PathVariable @Positive(message = "{id.positive}") Long id) {
        directionService.deleteDirectionById(id);
    }

    @GetMapping
    public Page<DirectionResponse> findAllDirections(Pageable pageable) {
        return directionService.findAllDirections(pageable);
    }

    @GetMapping("/{id}")
    public DirectionFullResponse findDirectionById(@PathVariable @Positive(message = "{id.positive}") Long id) {
        return directionService.findDirectionById(id);
    }

    @GetMapping("/recent")
    public List<DirectionResponse> findRecentDirections() {
        return directionService.findRecentDirections();
    }
}
