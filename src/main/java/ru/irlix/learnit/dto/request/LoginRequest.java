package ru.irlix.learnit.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "{login.not-blank}")
    @Size(min = 4, max = 320, message = "{login.size}")
    private String login;

    @NotBlank(message = "{password.not-blank}")
    @Pattern(regexp = "(?=.*\\d.*)(?=.*[A_Za-z].*)[A-Za-z\\d~`! @#$%^&*()_\\-+={\\[}\\]|:;\"'<,>.?/]+",
            message = "{password.format}")
    @Size(min = 6, max = 255, message = "{password.size}")
    private String password;
}
