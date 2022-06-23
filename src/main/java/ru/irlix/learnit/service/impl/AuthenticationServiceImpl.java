package ru.irlix.learnit.service.impl;

import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.dto.request.LoginRequest;
import ru.irlix.learnit.dto.request.RefreshTokenRequest;
import ru.irlix.learnit.dto.request.SignUpRequest;
import ru.irlix.learnit.dto.response.JwtResponse;
import ru.irlix.learnit.dto.response.user.UserFullResponse;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.exception.IncorrectCredentialsException;
import ru.irlix.learnit.exception.FieldAlreadyTakenException;
import ru.irlix.learnit.exception.NotFoundException;
import ru.irlix.learnit.exception.UnvalidatedJwtException;
import ru.irlix.learnit.mapper.UserMapper;
import ru.irlix.learnit.repository.UserRepository;
import ru.irlix.learnit.security.util.JwtUtils;
import ru.irlix.learnit.service.api.AuthenticationService;
import ru.irlix.learnit.service.helper.TokenHelper;
import ru.irlix.learnit.service.helper.UserHelper;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserHelper userHelper;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final TokenHelper tokenHelper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public JwtResponse login(LoginRequest request) {
        UserData userData = findUserOnAuthentication(request.getLogin());
        validatePassword(request, userData);
        return tokenHelper.generateTokens(userData);
    }

    @Override
    @Transactional
    public JwtResponse registerUser(SignUpRequest request) {
        validateUsernameAndEmail(request);
        UserData user = userMapper.mapToUser(request);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        UserData userData = userHelper.saveUser(user);
        return tokenHelper.generateTokens(userData);
    }

    @Override
    @Transactional
    public JwtResponse refreshTokens(RefreshTokenRequest request) {
        try {
            String username = jwtUtils.getUsernameFromJwt(request.getRefreshToken());
            UserData userData = userHelper.findUserByUsername(username);
            return tokenHelper.refreshTokens(userData, request);
        } catch (JWTDecodeException ex) {
            throw new UnvalidatedJwtException("Invalid token");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateAccessToken(String accessToken) {
        return tokenHelper.validateAccessToken(accessToken);
    }

    @Override
    @Transactional
    public void invalidateTokens() {
        UserData currentUserData = userHelper.getCurrentUserData();
        tokenHelper.invalidateTokens(currentUserData);
    }

    @Override
    @Transactional(readOnly = true)
    public UserFullResponse getCurrentUser() {
        UserData currentUserData = userHelper.getCurrentUserData();
        return userMapper.mapToFullResponse(currentUserData);
    }

    private void validateUsernameAndEmail(SignUpRequest request) {
        if (userHelper.existsUserByUserName(request.getUsername())) {
            throw new FieldAlreadyTakenException(request.getUsername(), "Username");
        }
        if (userHelper.existsUserByEmail(request.getEmail())) {
            throw new FieldAlreadyTakenException(request.getEmail(), "Email");
        }
    }

    private UserData findUserOnAuthentication(String login) {
        return userRepository.findUserDataByEmail(login)
                .orElseGet(() -> findUserOnAuthenticationByUsername(login));
    }

    private UserData findUserOnAuthenticationByUsername(String username) {
        try {
            return userHelper.findUserByUsername(username);
        } catch (NotFoundException ex) {
            throw new IncorrectCredentialsException();
        }
    }

    private void validatePassword(LoginRequest request, UserData userData) {
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), userData.getPassword());
        if (!passwordMatches) {
            throw new IncorrectCredentialsException();
        }
    }
}
