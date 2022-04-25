package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.dto.request.TopicRequest;
import ru.irlix.learnit.dto.response.topic.TopicFullResponse;
import ru.irlix.learnit.dto.response.topic.TopicResponse;
import ru.irlix.learnit.entity.Topic;
import ru.irlix.learnit.exception.NameAlreadyTakenException;
import ru.irlix.learnit.mapper.TopicMapper;
import ru.irlix.learnit.repository.TopicRepository;
import ru.irlix.learnit.service.api.TopicService;
import ru.irlix.learnit.service.helper.TopicHelper;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final TopicHelper topicHelper;

    @Override
    @Transactional
    public TopicFullResponse createTopic(TopicRequest request) {
        validateName(request);
        Topic topic = topicMapper.mapToEntity(request);
        Topic savedTopic = topicRepository.save(topic);
        log.info("New topic saved. Id = {}", savedTopic.getId());
        return topicMapper.mapToFullResponse(savedTopic);
    }

    @Override
    @Transactional
    public TopicFullResponse updateTopic(Long id, TopicRequest request) {
        Topic topicToUpdate = topicHelper.findTopicById(id);
        validateName(request, topicToUpdate);
        checkAndUpdateFields(request, topicToUpdate);
        Topic updatedTopic = topicRepository.save(topicToUpdate);
        log.info("Topic with id {} updated", id);
        return topicMapper.mapToFullResponse(updatedTopic);
    }

    @Override
    @Transactional
    public void deleteTopic(Long id) {
        Topic topic = topicHelper.findTopicById(id);
        topicRepository.delete(topic);
        log.info("Topic with id {} deleted", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicResponse> findTopicsByDirectionId(Long directionId, Pageable pageable) {
        Page<Topic> topics = topicRepository.findTopicsByDirectionsId(directionId, pageable);
        return topicMapper.mapToResponsePage(topics);
    }

    @Override
    @Transactional(readOnly = true)
    public TopicFullResponse findTopicById(Long id) {
        Topic topic = topicHelper.findTopicById(id);
        return topicMapper.mapToFullResponse(topic);
    }

    private void checkAndUpdateFields(TopicRequest request, Topic topicToUpdate) {
        Topic topic = topicMapper.mapToEntity(request);
        if (topic.getName() != null) {
            topicToUpdate.setName(topic.getName());
        }
        if (topic.getDescription() != null) {
            topicToUpdate.setDescription(topic.getDescription());
        }
        if (topic.getImage() != null) {
            topicToUpdate.setImage(topic.getImage());
        }
        if (topic.getDirections() != null) {
            topicToUpdate.setDirections(topic.getDirections());
        }
    }

    private void validateName(TopicRequest request) {
        String name = request.getName();
        boolean existsByName = topicRepository.existsByName(name);
        if (existsByName) {
            throw new NameAlreadyTakenException(name);
        }
    }

    private void validateName(TopicRequest request, Topic topic) {
        if (!Objects.equals(request.getName(), topic.getName())) {
            validateName(request);
        }
    }
}
