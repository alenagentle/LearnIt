package ru.irlix.learnit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.irlix.learnit.dto.request.ChangePasswordRequest;
import ru.irlix.learnit.dto.request.MailRequest;
import ru.irlix.learnit.service.api.MailService;
import ru.irlix.learnit.service.api.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restore")
@CrossOrigin
public class RestorePasswordController {

    private final MailService mailService;
    private final UserService userService;

    @PostMapping
    public void sendLinkToRestore(@RequestBody MailRequest mailRequest) {
        mailService.sendMessage(mailRequest.getMail());
    }

    @PostMapping("/password")
    public void changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        userService.changePassword(request);
    }
}