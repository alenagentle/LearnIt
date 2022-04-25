package ru.irlix.learnit.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.repository.AuthorityRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class AuthorityHelper {

    private final AuthorityRepository authorityRepository;

    public Collection<? extends GrantedAuthority> findAuthoritiesByUser(UserData user) {
        return authorityRepository.findAuthoritiesByRolesIn(user.getRoles())
                .stream().map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
}
