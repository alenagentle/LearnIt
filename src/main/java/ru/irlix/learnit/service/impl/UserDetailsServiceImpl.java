package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.exception.NotFoundException;
import ru.irlix.learnit.repository.UserRepository;
import ru.irlix.learnit.service.helper.AuthorityHelper;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityHelper authorityHelper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("User with username '%s' not found", username)));
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorityHelper.findAuthoritiesByUser(user))
                .build();
    }
}
