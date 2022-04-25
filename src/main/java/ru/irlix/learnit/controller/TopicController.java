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
import ru.irlix.learnit.dto.request.TopicRequest;
import ru.irlix.learnit.dto.response.topic.TopicFullResponse;
import ru.irlix.learnit.dto.response.topic.TopicResponse;
import ru.irlix.learnit.service.api.TopicService;

@RestController
@RequestMapping("/api/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    public TopicFullResponse createTopic(@RequestBody TopicRequest topicRequest) {
        return topicService.createTopic(topicRequest);
    }

    @PutMapping("/{id}")
    public TopicFullResponse updateTopic(@PathVariable Long id, @RequestBody TopicRequest topicRequest) {
        return topicService.updateTopic(id, topicRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
    }

    @GetMapping
    public Page<TopicResponse> findTopicsByDirectionId(Long directionId, Pageable pageable) {
        return topicService.findTopicsByDirectionId(directionId, pageable);
    }

    @GetMapping("/{id}")
    public TopicFullResponse findTopicById(@PathVariable Long id) {
        return topicService.findTopicById(id);
    }
}
