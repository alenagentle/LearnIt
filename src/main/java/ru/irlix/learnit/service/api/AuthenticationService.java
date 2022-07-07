package ru.irlix.learnit.service.api;

import ru.irlix.learnit.dto.request.LoginRequest;
import ru.irlix.learnit.dto.request.RefreshTokenRequest;
import ru.irlix.learnit.dto.request.SignUpRequest;
import ru.irlix.learnit.dto.response.JwtResponse;
import ru.irlix.learnit.dto.response.user.UserFullResponse;

public interface AuthenticationService {

    JwtResponse login(LoginRequest loginRequest);

    JwtResponse registerUser(SignUpRequest request);

    JwtResponse refreshTokens(RefreshTokenRequest request);

    boolean validateAccessToken(String accessToken);

    void invalidateTokens();

    UserFullResponse getCurrentUser();

}
