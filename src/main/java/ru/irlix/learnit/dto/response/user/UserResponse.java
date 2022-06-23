package ru.irlix.learnit.dto.response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;

    private String username;

    private String name;

    private String surname;
}
