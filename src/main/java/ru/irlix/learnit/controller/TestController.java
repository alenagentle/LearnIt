package ru.irlix.learnit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.irlix.learnit.config.validation.group.OnCreateGroup;
import ru.irlix.learnit.config.validation.group.OnUpdateGroup;
import ru.irlix.learnit.dto.request.TestRequest;
import ru.irlix.learnit.dto.response.test.TestFullResponse;
import ru.irlix.learnit.dto.response.test.TestResponse;
import ru.irlix.learnit.service.api.TestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
@Validated
@CrossOrigin
public class TestController {

    private final TestService testService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Validated(OnCreateGroup.class)
    public TestFullResponse createTest(@Valid TestRequest request) {
        return testService.createTest(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Validated(OnUpdateGroup.class)
    public TestFullResponse updateTest(@PathVariable
                                       @Positive(groups = OnUpdateGroup.class,
                                               message = "{id.positive}") Long id,
                                       @Valid TestRequest request) {
        return testService.updateTest(id, request);
    }

    @GetMapping
    public Page<TestResponse> findTestsByTopicId(@Positive(message = "{topic-id.positive}") Long topicId,
                                                 Pageable pageable) {
        return testService.findTestsByTopicId(topicId, pageable);
    }

    @GetMapping("/{id}")
    public TestFullResponse findTestById(@PathVariable @Positive(message = "{id.positive}") Long id) {
        return testService.findTestById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable @Positive(message = "{id.positive}") Long id) {
        testService.deleteTestById(id);
    }
}
