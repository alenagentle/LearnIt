package ru.irlix.learnit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.irlix.learnit.dto.request.SignupRequest;
import ru.irlix.learnit.entity.Role;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.repository.RoleName;
import ru.irlix.learnit.service.helper.RoleHelper;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private RoleHelper roleHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "password", ignore = true)
    public abstract UserData mapToUser(SignupRequest signupRequest);

    @AfterMapping
    protected void map(@MappingTarget UserData user, SignupRequest signupRequest) {
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        user.setPassword(encodedPassword);
        Role defaultRole = roleHelper.findByName(RoleName.ROLE_USER);
        user.setRoles(Set.of(defaultRole));
    }
}
