package ru.irlix.learnit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.dto.request.ChangePasswordRequest;
import ru.irlix.learnit.dto.response.user.UserFullResponse;
import ru.irlix.learnit.entity.Role;
import ru.irlix.learnit.entity.UserData;
import ru.irlix.learnit.entity.enums.RoleName;
import ru.irlix.learnit.exception.InvalidRecoveryCode;
import ru.irlix.learnit.mapper.UserMapper;
import ru.irlix.learnit.repository.UserRepository;
import ru.irlix.learnit.security.util.SecurityUtil;
import ru.irlix.learnit.service.api.UserService;
import ru.irlix.learnit.service.helper.RoleHelper;
import ru.irlix.learnit.service.helper.UserHelper;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserHelper userHelper;
    private final UserMapper userMapper;
    private final RoleHelper roleHelper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void deleteUser(String username) {
        UserData user = userHelper.findUserByUsername(username);
        if (!SecurityUtil.hasAdminRoles() && !user.equals(userHelper.getCurrentUserData())) {
            throw new AccessDeniedException(String
                    .format("User with username '%s' can't get access to delete user with username '%s'",
                            userHelper.getCurrentUserData().getUsername(),
                            user.getUsername()));
        }
        userRepository.delete(user);
        log.info(String.format("User with username '%s' deleted", username));
    }

    @Override
    @Transactional
    public UserFullResponse setAdminRole(String username) {
        UserData user = userHelper.findUserByUsername(username);
        Role role = roleHelper.findRoleByRoleName(RoleName.ROLE_ADMIN);
        if (user.getRoles().contains(role)) {
            user.getRoles().remove(role);
        } else {
            user.getRoles().add(role);
        }
        userRepository.save(user);
        return userMapper.mapToFullResponse(user);
    }

    @Override
    @Transactional
    public List<UserFullResponse> findAllUsers() {
        List<UserData> userDataList = userRepository.findAll();
        return userMapper.mapToFullResponses(userDataList);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        UserData user = userHelper.findUserByEmail(request.getEmail());
        if (user.getRestoreCode() != null && user.getRestoreCode().equals(request.getRecoveryCode())) {
            String encodedPassword = passwordEncoder.encode(request.getNewPassword());
            user.setPassword(encodedPassword);
            user.setRestoreCode(null);
            userHelper.saveUser(user);
            log.info("Password changed successfully");
        } else throw new InvalidRecoveryCode("Invalid recovery code");
    }
}
