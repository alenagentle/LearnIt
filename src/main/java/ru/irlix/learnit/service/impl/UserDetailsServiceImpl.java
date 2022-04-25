package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.exception.NotFoundException;
import ru.irlix.learnit.mapper.UserMapper;
import ru.irlix.learnit.repository.UserRepository;
import ru.irlix.learnit.service.helper.UserHelper;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserHelper userHelper;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData user = userHelper.findUserByUsername(username);
        return userMapper.mapToUserDetails(user);
    }
}
