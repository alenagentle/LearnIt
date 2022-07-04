package ru.irlix.learnit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.irlix.learnit.dto.request.MailRequest;
import ru.irlix.learnit.service.api.AuthenticationService;
import ru.irlix.learnit.service.api.MailService;
import ru.irlix.learnit.service.helper.UserHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restore")
@CrossOrigin
public class RestorePasswordController {

    private final MailService mailService;
    private final AuthenticationService authenticationService;
    private UserHelper userHelper;

    @PostMapping
    public void sendLinkToRestore(@RequestBody MailRequest mailRequest) {
        mailService.sendMessage(mailRequest.getMail());
    }

    @GetMapping("/{token}")
    public void getToken(@PathVariable String token, HttpServletResponse response) {
        System.out.println("/api/restore = " + token);
        response.setHeader("Authorization", authenticationService.getToken(token).getAccessToken());
        System.out.println("response.getHeaderAuthorization=" + response.getHeader("Authorization"));
        try {
            response.sendRedirect("localhost:8080/api/direction");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}