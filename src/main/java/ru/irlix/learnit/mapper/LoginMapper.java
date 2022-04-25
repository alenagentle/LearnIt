package ru.irlix.learnit.mapper;

import org.mapstruct.Mapper;
import ru.irlix.learnit.dto.request.LoginRequest;
import ru.irlix.learnit.dto.request.SignupRequest;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    LoginRequest mapToLogin(SignupRequest signupRequest);
}
