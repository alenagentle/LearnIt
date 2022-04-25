package ru.irlix.learnit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.irlix.learnit.dto.request.LoginRequest;
import ru.irlix.learnit.dto.request.SignUpRequest;
import ru.irlix.learnit.dto.response.JwtResponse;
import ru.irlix.learnit.service.impl.AuthenticationServiceImpl;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/sign-in")
    public JwtResponse authenticateUser(@RequestBody LoginRequest request) {
        return authenticationService.getJwt(request);
    }

    @PostMapping("/sign-up")
    public JwtResponse registerUser(@RequestBody SignUpRequest request) {
        return authenticationService.registerUser(request);
    }
}