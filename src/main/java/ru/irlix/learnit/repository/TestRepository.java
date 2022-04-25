package ru.irlix.learnit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.irlix.learnit.entity.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> { }
