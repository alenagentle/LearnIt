package ru.irlix.learnit.mapper;

import org.mapstruct.Mapper;
import ru.irlix.learnit.dto.response.JwtResponse;
import ru.irlix.learnit.entity.Token;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.security.dto.TokenInfo;

@Mapper(componentModel = "spring")
public abstract class TokenMapper {

    public Token mapToEntity(UserData userData, TokenInfo accessToken, TokenInfo refreshToken) {
        Token token = new Token();
        token.setUserData(userData);
        token.setAccessToken(accessToken.getToken());
        token.setRefreshToken(refreshToken.getToken());
        token.setAccessTokenExpires(accessToken.getExpiresAt());
        token.setRefreshTokenExpires(refreshToken.getExpiresAt());
        return token;
    }

    public abstract JwtResponse mapToResponse(Token token);
}
