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
import ru.irlix.learnit.dto.request.QuestionRequest;
import ru.irlix.learnit.dto.response.question.QuestionResponse;
import ru.irlix.learnit.service.api.QuestionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public QuestionResponse createQuestion(@RequestBody QuestionRequest questionRequest) {
        return questionService.createQuestion(questionRequest);
    }

    @GetMapping("/{id}")
    public QuestionResponse findQuestionById(@PathVariable("id") Long id) {
        return questionService.findQuestionById(id);
    }

    @GetMapping
    public List<QuestionResponse> findQuestionsByTestId(Long id) {
        return questionService.findQuestionsByTestId(id);
    }

    @PutMapping("/{id}")
    public QuestionResponse updateQuestion(@PathVariable("id") Long id,
                                           @RequestBody QuestionRequest questionRequest) {
        return questionService.updateQuestion(id, questionRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteQuestion(id);
    }
}
