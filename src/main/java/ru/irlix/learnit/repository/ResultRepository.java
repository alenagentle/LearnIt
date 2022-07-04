package ru.irlix.learnit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.irlix.learnit.entity.Result;
import ru.irlix.learnit.entity.UserData;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findResultsByTestIdAndUserId(Long testId, Long userId);

    @Query("select r from Result r where r.user=:user order by r.lastUpdate desc")
    List<Result> findRecentResult(@Param("user") UserData user);
}
