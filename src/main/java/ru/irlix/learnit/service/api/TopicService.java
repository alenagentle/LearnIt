package ru.irlix.learnit.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.irlix.learnit.dto.request.TopicRequest;
import ru.irlix.learnit.dto.response.topic.TopicFullResponse;
import ru.irlix.learnit.dto.response.topic.TopicResponse;

public interface TopicService {

    TopicFullResponse createTopic(TopicRequest topicRequest);

    TopicFullResponse updateTopic(Long id, TopicRequest topicRequest);

    void deleteTopicById(Long id);

    Page<TopicResponse> findTopicsByDirectionId(Long directionId, Pageable pageable);

    TopicFullResponse findTopicById(Long id);
}
