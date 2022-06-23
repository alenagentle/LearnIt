package ru.irlix.learnit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.irlix.learnit.dto.request.LoginRequest;
import ru.irlix.learnit.dto.request.RefreshTokenRequest;
import ru.irlix.learnit.dto.request.SignUpRequest;
import ru.irlix.learnit.dto.response.JwtResponse;
import ru.irlix.learnit.dto.response.user.UserFullResponse;
import ru.irlix.learnit.service.api.AuthenticationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    public JwtResponse authenticateUser(@RequestBody @Valid LoginRequest request) {
        return authenticationService.login(request);
    }

    @PostMapping("/sign-up")
    public JwtResponse registerUser(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.registerUser(request);
    }

    @PostMapping("/refresh")
    public JwtResponse refreshTokens(@RequestBody @Valid RefreshTokenRequest request) {
        return authenticationService.refreshTokens(request);
    }

    @PostMapping("/invalidate")
    public void invalidateTokens() {
        authenticationService.invalidateTokens();
    }

    @GetMapping("/info")
    public UserFullResponse getUserInfo() {
        return authenticationService.getCurrentUser();
    }
}