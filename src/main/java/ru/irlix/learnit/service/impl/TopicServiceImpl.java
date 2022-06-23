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
import ru.irlix.learnit.entity.Direction;
import ru.irlix.learnit.entity.Image;
import ru.irlix.learnit.entity.Topic;
import ru.irlix.learnit.exception.FieldAlreadyTakenException;
import ru.irlix.learnit.exception.NoRelatedDirectionException;
import ru.irlix.learnit.mapper.TopicMapper;
import ru.irlix.learnit.repository.TopicRepository;
import ru.irlix.learnit.service.api.TopicService;
import ru.irlix.learnit.service.helper.DirectionHelper;
import ru.irlix.learnit.service.helper.FileHelper;
import ru.irlix.learnit.service.helper.TopicHelper;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final TopicHelper topicHelper;
    private final DirectionHelper directionHelper;
    private final FileHelper fileHelper;

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
    public void deleteTopicById(Long id) {
        Topic topic = topicHelper.findTopicById(id);
        topicRepository.delete(topic);
        log.info("Topic with id {} deleted", id);
        deleteImageFromS3(topic);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicResponse> findTopicsByDirectionId(Long directionId, Pageable pageable) {
        directionHelper.checkDirectionExistingById(directionId);
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
        if (request.getName() != null) {
            topicToUpdate.setName(request.getName());
        }
        if (request.getDescription() != null) {
            topicToUpdate.setDescription(request.getDescription());
        }
        if (request.getImage() != null) {
            deleteImageFromS3(topicToUpdate);
            Image image = fileHelper.saveImageOnS3(request.getImage());
            topicToUpdate.setImage(image);
        }
        if (request.getDirectionIdSet() != null && !request.getDirectionIdSet().isEmpty()) {
            List<Direction> directions = directionHelper.findDirectionsByIds(request.getDirectionIdSet());
            if (directions.isEmpty()) {
                throw new NoRelatedDirectionException();
            }
            topicToUpdate.setDirections(directions);
        }
        if (request.getWikiText() != null) {
            topicToUpdate.getWiki().setText(request.getWikiText());
        }
    }

    private void deleteImageFromS3(Topic topic) {
        if (topic.getImage() == null) {
            return;
        }
        String key = topic.getImage().getKey();
        fileHelper.deleteImageFromS3(key);
    }

    private void validateName(TopicRequest request) {
        String name = request.getName();
        boolean existsByName = topicRepository.existsByName(name);
        if (existsByName) {
            throw new FieldAlreadyTakenException(name, "Name");
        }
    }

    private void validateName(TopicRequest request, Topic topic) {
        if (!Objects.equals(request.getName(), topic.getName())) {
            validateName(request);
        }
    }
}
