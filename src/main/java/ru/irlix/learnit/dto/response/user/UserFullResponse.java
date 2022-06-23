package ru.irlix.learnit.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import ru.irlix.learnit.dto.response.result.ResultResponse;
import ru.irlix.learnit.dto.response.test.TestResponse;
import ru.irlix.learnit.entity.enums.RoleName;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFullResponse {

    private Long id;

    private String username;

    private String email;

    private Instant registrationDate;

    private String name;

    private String surname;

    private List<RoleName> roles;

    private List<TestResponse> createdTests = new ArrayList<>();

    private List<ResultResponse> results = new ArrayList<>();
}
