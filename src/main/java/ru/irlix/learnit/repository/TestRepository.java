package ru.irlix.learnit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.irlix.learnit.entity.Test;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    Page<Test> findTestsByTopicId(Long topicId, Pageable pageable);

    @Query(value = "SELECT DISTINCT t FROM Test t " +
            "INNER JOIN FETCH t.results r " +
            "WHERE r.user.id = :userId " +
            "GROUP BY t, r")
    List<Test> findTestsWithResultsByUserId(@Param("userId") Long userId);
}
