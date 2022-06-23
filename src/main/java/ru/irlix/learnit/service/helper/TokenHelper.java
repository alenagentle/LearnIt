package ru.irlix.learnit.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.dto.request.RefreshTokenRequest;
import ru.irlix.learnit.dto.response.JwtResponse;
import ru.irlix.learnit.entity.Token;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.exception.NotFoundException;
import ru.irlix.learnit.exception.UnvalidatedJwtException;
import ru.irlix.learnit.mapper.TokenMapper;
import ru.irlix.learnit.repository.TokenRepository;
import ru.irlix.learnit.security.dto.TokenInfo;
import ru.irlix.learnit.security.util.JwtUtils;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class TokenHelper {

    private final JwtUtils jwtUtils;
    private final TokenMapper tokenMapper;
    private final TokenRepository tokenRepository;

    public JwtResponse generateTokens(UserData userData) {
        Token token = getToken(userData);
        tokenRepository.save(token);
        return tokenMapper.mapToResponse(token);
    }

    public JwtResponse refreshTokens(UserData userData, RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        validateRefreshToken(refreshToken);
        return generateTokens(userData);
    }

    public void invalidateTokens(UserData userData) {
        userData.setToken(null);
        tokenRepository.deleteTokenByUserData(userData);
    }

    private Token getToken(UserData userData) {
        String login = userData.getUsername();
        TokenInfo accessTokenInfo = jwtUtils.generateAccessToken(login);
        TokenInfo refreshTokenInfo = jwtUtils.generateRefreshToken(login);
        boolean tokenExists = tokenRepository.existsTokensByUserData(userData);
        if (tokenExists) {
            return updateToken(userData, accessTokenInfo, refreshTokenInfo);
        }

        return tokenMapper.mapToEntity(userData, accessTokenInfo, refreshTokenInfo);
    }

    private Token updateToken(UserData userData, TokenInfo accessTokenInfo, TokenInfo refreshTokenInfo) {
        Token token = tokenRepository.findTokenByUserData(userData)
                .orElseThrow(() -> new NotFoundException("Token not found"));
        token.setAccessToken(accessTokenInfo.getToken());
        token.setRefreshToken(refreshTokenInfo.getToken());
        token.setAccessTokenExpires(accessTokenInfo.getExpiresAt());
        token.setRefreshTokenExpires(refreshTokenInfo.getExpiresAt());
        return token;
    }

    private void validateRefreshToken(String refreshToken) {
        jwtUtils.validateJwt(refreshToken);
        boolean tokenExists = tokenRepository.existsTokenByRefreshTokenAndRefreshTokenExpiresGreaterThanEqual(
                refreshToken, Instant.now());
        if (!tokenExists) {
            throw new UnvalidatedJwtException("Invalid token");
        }
    }

    public boolean validateAccessToken(String accessToken) {
        return tokenRepository.existsTokenByAccessTokenAndAccessTokenExpiresGreaterThanEqual(accessToken, Instant.now());
    }
}
