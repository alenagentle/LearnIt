package ru.irlix.learnit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.irlix.learnit.config.validation.group.OnCreateGroup;
import ru.irlix.learnit.config.validation.group.OnUpdateGroup;
import ru.irlix.learnit.dto.request.AnswerFullRequest;
import ru.irlix.learnit.dto.response.answer.AnswerFullResponse;
import ru.irlix.learnit.dto.response.answer.AnswerResponse;
import ru.irlix.learnit.service.api.AnswerService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answer")
@Validated
@CrossOrigin
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    @Validated(OnCreateGroup.class)
    public AnswerFullResponse createAnswer(@RequestBody @Valid AnswerFullRequest request) {
        return answerService.createAnswer(request);
    }

    @PostMapping("/list")
    @Validated(OnCreateGroup.class)
    public List<AnswerResponse> createAnswers(@RequestBody @Valid List<AnswerFullRequest> requests) {
        return answerService.createAnswers(requests);
    }

    @PutMapping("/{id}")
    @Validated(OnUpdateGroup.class)
    public AnswerFullResponse updateAnswer(@PathVariable
                                           @Positive(groups = OnUpdateGroup.class,
                                                   message = "{id.positive}") Long id,
                                           @RequestBody @Valid AnswerFullRequest request) {
        return answerService.updateAnswer(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteAnswer(@PathVariable @Positive(message = "{id.positive}") Long id) {
        answerService.deleteAnswer(id);
    }

    @GetMapping("/{id}")
    public AnswerFullResponse findAnswerById(@PathVariable @Positive(message = "{id.positive}") Long id) {
        return answerService.findAnswerById(id);
    }

    @GetMapping
    public List<AnswerResponse> findAnswersByQuestionId(@Positive(message = "{question-id.positive}") Long questionId) {
        return answerService.findAnswersByQuestionId(questionId);
    }
}
