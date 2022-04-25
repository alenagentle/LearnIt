package ru.irlix.learnit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.irlix.learnit.dto.request.AnswerRequest;
import ru.irlix.learnit.dto.response.answer.AnswerFullResponse;
import ru.irlix.learnit.dto.response.answer.AnswerResponse;
import ru.irlix.learnit.service.api.AnswerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answer")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public AnswerFullResponse createAnswer(AnswerRequest request) {
        return answerService.createAnswer(request);
    }

    @GetMapping("/{id}")
    public AnswerFullResponse findAnswerById(@PathVariable Long id) {
        return answerService.findAnswerById(id);
    }

    @GetMapping
    public List<AnswerResponse> findAnswersByQuestionId(Long questionId) {
        return answerService.findAnswersByQuestionId(questionId);
    }

    @PutMapping("/{id}")
    public AnswerFullResponse updateAnswer(@PathVariable Long id, @RequestBody AnswerRequest request) {
        return answerService.updateAnswer(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
    }
}
