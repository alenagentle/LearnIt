package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.dto.request.LoginRequest;
import ru.irlix.learnit.dto.request.SignUpRequest;
import ru.irlix.learnit.dto.response.JwtResponse;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.exception.IncorrectCredentialsException;
import ru.irlix.learnit.exception.NameAlreadyTakenException;
import ru.irlix.learnit.mapper.UserMapper;
import ru.irlix.learnit.security.util.JwtUtils;
import ru.irlix.learnit.service.api.AuthenticationService;
import ru.irlix.learnit.service.helper.UserHelper;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtUtils jwtUtils;
    private final UserHelper userHelper;
    private final UserMapper userMapper;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public JwtResponse getJwt(LoginRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), userDetails.getPassword());
        if (!passwordMatches) {
            throw new IncorrectCredentialsException();
        }

        String jwt = jwtUtils.generateJwtToken(request.getUsername());
        return new JwtResponse(jwt);
    }

    @Override
    @Transactional
    public JwtResponse registerUser(SignUpRequest request) {
        if (userHelper.existsUserByUserName(request.getUsername())) {
            throw new NameAlreadyTakenException(request.getUsername());
        }
        UserData user = userMapper.mapToUser(request);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userHelper.saveUser(user);
        String jwt = jwtUtils.generateJwtToken(user.getUsername());
        return new JwtResponse(jwt);
    }
}
