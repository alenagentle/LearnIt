package ru.irlix.learnit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.irlix.learnit.entity.UserData;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserData, Long> {

    Optional<UserData> findByUsername(String username);

    Boolean existsByUsername(String username);
}
