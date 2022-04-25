package ru.irlix.learnit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.irlix.learnit.entity.Direction;

import java.util.Collection;
import java.util.List;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long> {

    List<Direction> findDirectionByIdIn(Collection<Long> id);

    boolean existsByName(String name);
}