package ru.irlix.learnit.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.repository.UserRepository;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class UserHelper {

    private final UserRepository userRepository;

    public Boolean existsUserByUserName(String username) {
        return userRepository.existsByUsername(username);
    }

    public void saveUser(UserData user) {
        userRepository.save(user);
    }
}
