package ru.irlix.learnit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.irlix.learnit.entity.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
}
