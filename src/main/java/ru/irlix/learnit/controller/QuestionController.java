package ru.irlix.learnit.controller;

import lombok.RequiredArgsConstructor;
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
import ru.irlix.learnit.dto.request.QuestionRequest;
import ru.irlix.learnit.dto.response.question.QuestionResponse;
import ru.irlix.learnit.service.api.QuestionService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
@Validated
@CrossOrigin
public class QuestionController {

    private final QuestionService questionService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Validated(OnCreateGroup.class)
    public QuestionResponse createQuestion(@Valid QuestionRequest request) {
        return questionService.createQuestion(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Validated(OnUpdateGroup.class)
    public QuestionResponse updateQuestion(@PathVariable
                                           @Positive(groups = OnUpdateGroup.class,
                                                   message = "{id.positive}") Long id,
                                           @Valid QuestionRequest request) {
        return questionService.updateQuestion(id, request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable @Positive(message = "{id.positive}") Long id) {
        questionService.deleteQuestionById(id);
    }

    @GetMapping("/{id}")
    public QuestionResponse findQuestionById(@PathVariable @Positive(message = "{id.positive}") Long id) {
        return questionService.findQuestionById(id);
    }

    @GetMapping
    public List<QuestionResponse> findQuestionsByTestId(@Positive(message = "{test-id.positive}") Long testId) {
        return questionService.findQuestionsByTestId(testId);
    }
}
