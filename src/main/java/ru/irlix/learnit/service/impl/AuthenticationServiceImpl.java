package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.dto.request.LoginRequest;
import ru.irlix.learnit.dto.request.SignupRequest;
import ru.irlix.learnit.dto.response.JwtResponse;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.exception.NameAlreadyTakenException;
import ru.irlix.learnit.mapper.LoginMapper;
import ru.irlix.learnit.mapper.UserMapper;
import ru.irlix.learnit.security.util.JwtUtils;
import ru.irlix.learnit.service.api.AuthenticationService;
import ru.irlix.learnit.service.helper.UserHelper;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserHelper userHelper;
    private final UserMapper userMapper;
    private final UserDetailsService userDetailsService;
    private final LoginMapper loginMapper;

    @Override
    public JwtResponse getJwt(LoginRequest loginRequest) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userDetails,
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        return new JwtResponse(jwt);
    }

    @Override
    @Transactional
    public JwtResponse registerUser(SignupRequest signupRequest) {
        if (userHelper.existsUserByUserName(signupRequest.getUsername())) {
            throw new NameAlreadyTakenException(signupRequest.getUsername());
        }
        UserData user = userMapper.mapToUser(signupRequest);
        userHelper.saveUser(user);
        return getJwt(loginMapper.mapToLogin(signupRequest));
    }
}
