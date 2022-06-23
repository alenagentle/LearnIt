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
import ru.irlix.learnit.dto.request.TopicRequest;
import ru.irlix.learnit.dto.response.topic.TopicFullResponse;
import ru.irlix.learnit.dto.response.topic.TopicResponse;
import ru.irlix.learnit.service.api.TopicService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/topic")
@RequiredArgsConstructor
@Validated
@CrossOrigin
public class TopicController {

    private final TopicService topicService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Validated(OnCreateGroup.class)
    public TopicFullResponse createTopic(@Valid TopicRequest topicRequest) {
        return topicService.createTopic(topicRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Validated(OnUpdateGroup.class)
    public TopicFullResponse updateTopic(@PathVariable
                                         @Positive(groups = OnUpdateGroup.class,
                                                 message = "{id.positive}") Long id,
                                         @Valid TopicRequest topicRequest) {
        return topicService.updateTopic(id, topicRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTopic(@PathVariable @Positive(message = "{id.positive}") Long id) {
        topicService.deleteTopicById(id);
    }

    @GetMapping
    public Page<TopicResponse> findTopicsByDirectionId(@Positive(message = "{direction-id.positive}") Long directionId,
                                                       Pageable pageable) {
        return topicService.findTopicsByDirectionId(directionId, pageable);
    }

    @GetMapping("/{id}")
    public TopicFullResponse findTopicById(@PathVariable @Positive(message = "{id.positive}") Long id) {
        return topicService.findTopicById(id);
    }
}
