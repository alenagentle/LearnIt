package ru.irlix.learnit.service.api;

import ru.irlix.learnit.dto.request.LoginRequest;
import ru.irlix.learnit.dto.request.SignUpRequest;
import ru.irlix.learnit.dto.response.JwtResponse;

public interface AuthenticationService {

    JwtResponse getJwt(LoginRequest loginRequest);

    JwtResponse registerUser(SignUpRequest request);
}
