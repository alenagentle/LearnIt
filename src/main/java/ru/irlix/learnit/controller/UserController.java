package ru.irlix.learnit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.irlix.learnit.dto.response.user.UserFullResponse;
import ru.irlix.learnit.service.api.UserService;

import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable @Size(min = 4, max = 320, message = "{login.size}") String username) {
        userService.deleteUser(username);
    }

    @PutMapping("/admin/{username}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public UserFullResponse setAdminRole(@PathVariable @Size(min = 4, max = 320, message = "{login.size}") String username) {
        return userService.setAdminRole(username);
    }

    @GetMapping
    public List<UserFullResponse> findAllUsers() {
        return userService.findAllUsers();
    }
}
