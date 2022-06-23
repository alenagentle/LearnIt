package ru.irlix.learnit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.irlix.learnit.entity.Token;
import ru.irlix.learnit.entity.UserData;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    boolean existsTokenByRefreshTokenAndRefreshTokenExpiresGreaterThanEqual(String refreshToken, Instant currentDate);

    boolean existsTokenByAccessTokenAndAccessTokenExpiresGreaterThanEqual(String accessToken, Instant currentDate);

    boolean existsTokensByUserData(UserData userData);

    Optional<Token> findTokenByUserData(UserData userData);

    void deleteTokenByUserData(UserData userData);
}