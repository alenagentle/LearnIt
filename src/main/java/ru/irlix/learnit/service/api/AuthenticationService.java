package ru.irlix.learnit.service.api;

import ru.irlix.learnit.dto.request.LoginRequest;
import ru.irlix.learnit.dto.request.SignupRequest;
import ru.irlix.learnit.dto.response.JwtResponse;

public interface AuthenticationService {

    JwtResponse getJwt(LoginRequest loginRequest);

    JwtResponse registerUser(SignupRequest signupRequest);
}
