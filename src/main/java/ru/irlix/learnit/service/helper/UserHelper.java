package ru.irlix.learnit.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.exception.NotFoundException;
import ru.irlix.learnit.repository.UserRepository;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class UserHelper {

    private final UserRepository userRepository;

    public Boolean existsUserByUserName(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserData saveUser(UserData user) {
        return userRepository.save(user);
    }

    public UserData findUserByUsername(String username) {
        return userRepository.findUserDataByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("User with username '%s' not found", username)));
    }

    public UserData getCurrentUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String anonymousUser = "anonymousUser";
        if (authentication.getPrincipal() == anonymousUser) {
            return null;
        }
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        return findUserByUsername(username);
    }
}
