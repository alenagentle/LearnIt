package ru.irlix.learnit.service.api;

import ru.irlix.learnit.dto.request.ChangePasswordRequest;
import ru.irlix.learnit.dto.response.user.UserFullResponse;

import java.util.List;

public interface UserService {

    void deleteUser(String username);

    UserFullResponse setAdminRole(String username);

    List<UserFullResponse> findAllUsers();

    void changePassword(ChangePasswordRequest request);
}
