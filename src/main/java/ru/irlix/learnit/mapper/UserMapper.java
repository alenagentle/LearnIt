package ru.irlix.learnit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ru.irlix.learnit.dto.request.SignUpRequest;
import ru.irlix.learnit.entity.Role;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.repository.RoleName;
import ru.irlix.learnit.service.helper.AuthorityHelper;
import ru.irlix.learnit.service.helper.RoleHelper;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private RoleHelper roleHelper;

    @Autowired
    private AuthorityHelper authorityHelper;

    public abstract UserData mapToUser(SignUpRequest signupRequest);

    public UserDetails mapToUserDetails(UserData user) {
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorityHelper.findAuthoritiesByUser(user))
                .build();
    }

    @AfterMapping
    protected void map(@MappingTarget UserData user) {
        Role defaultRole = roleHelper.findByName(RoleName.ROLE_USER);
        user.setRoles(Set.of(defaultRole));
    }
}
