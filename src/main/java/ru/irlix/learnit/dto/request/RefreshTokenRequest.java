package ru.irlix.learnit.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RefreshTokenRequest {

    @NotBlank(message = "{refresh.not-null}")
    private String refreshToken;
}
