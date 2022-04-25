package ru.irlix.learnit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.irlix.learnit.entity.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findTopicsByDirectionsId(Long descriptionId, Pageable pageable);

    boolean existsByName(String name);
}