package ru.irlix.learnit.dto.request;

import lombok.Getter;
import lombok.Setter;
import ru.irlix.learnit.config.validation.annotation.NullOrNotBlank;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank(message = "{username.not-blank}")
    @Pattern(regexp = "^(?=.*[A-Za-z\\d]$)[A-Za-z][A-Za-z\\d.-]*$", message = "{username.format}")
    @Size(min = 4, max = 32, message = "{username.size}")
    private String username;

    @NotBlank(message = "{mail.not-blank}")
    @Pattern(regexp = "^[a-zA-Z\\d_.+-]{2,}+@[a-zA-Z\\d-]{2,}\\.[a-zA-Z\\d-.]{2,}$", message = "{mail.format}")
    @Size(min = 8, max = 320, message = "{mail.size}")
    private String email;

    @Size(min = 2, max = 255, message = "{name.size}")
    @NullOrNotBlank(message = "{name.not-blank}")
    private String name;

    @Size(min = 2, max = 255, message = "{surname.size}")
    @NullOrNotBlank(message = "{surname.not-blank}")
    private String surname;

    @NotBlank(message = "{password.not-blank}")
    @Pattern(regexp = "(?=.*\\d.*)(?=.*[A_Za-z].*)[A-Za-z\\d~`! @#$%^&*()_\\-+={\\[}\\]|:;\"'<,>.?/]+",
            message = "{password.format}")
    @Size(min = 6, max = 255, message = "{password.size}")
    private String password;
}