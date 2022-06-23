package ru.irlix.learnit.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.entity.Topic;
import ru.irlix.learnit.exception.NotFoundException;
import ru.irlix.learnit.repository.TopicRepository;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class TopicHelper {

    private final TopicRepository topicRepository;

    public Topic findTopicById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Cant find topic with id %d", id)));
    }

    public void checkTopicExistingById(Long id) {
        boolean topicExists = topicRepository.existsById(id);
        if (!topicExists) {
            throw new NotFoundException(String.format("Cant find topic with id %d", id));
        }
    }
}
