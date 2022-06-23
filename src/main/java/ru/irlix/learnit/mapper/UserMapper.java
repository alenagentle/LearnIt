package ru.irlix.learnit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ru.irlix.learnit.dto.request.SignUpRequest;
import ru.irlix.learnit.dto.response.user.UserFullResponse;
import ru.irlix.learnit.dto.response.user.UserResponse;
import ru.irlix.learnit.entity.Role;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.entity.enums.RoleName;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {TestMapper.class, ResultMapper.class})
public abstract class UserMapper {

    public abstract UserData mapToUser(SignUpRequest signupRequest);

    public abstract UserResponse mapToResponse(UserData userData);

    @Mapping(target = "createdTests", source = "tests")
    @Mapping(target = "roles", ignore = true)
    public abstract UserFullResponse mapToFullResponse(UserData userData);

    public abstract List<UserFullResponse> mapToFullResponses(List<UserData> userDataList);

    public UserDetails mapToUserDetails(UserData user) {
        List<String> roles = user.getRoles().stream()
                .map(r -> r.getName().name())
                .toList();

        List<String> authorities = user.getRoles().stream()
                .flatMap(r -> r.getAuthorities().stream())
                .map(a -> a.getName().name())
                .collect(Collectors.toList());

        authorities.addAll(roles);

        List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(grantedAuthorities)
                .build();
    }

    @AfterMapping
    protected void map(@MappingTarget UserFullResponse response, UserData user) {
        List<RoleName> roles = user.getRoles().stream().map(Role::getName).toList();
        response.setRoles(roles);
    }
}
