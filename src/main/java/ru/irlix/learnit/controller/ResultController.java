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
import ru.irlix.learnit.dto.request.ResultRequest;
import ru.irlix.learnit.dto.response.result.ResultResponse;
import ru.irlix.learnit.dto.response.result.FinalResultResponse;
import ru.irlix.learnit.service.api.ResultService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/result")
public class ResultController {

    private final ResultService resultService;

    @PostMapping
    public ResultResponse createResult(@RequestBody ResultRequest resultRequest) {
        return resultService.createResult(resultRequest);
    }

    @GetMapping("/{id}")
    public ResultResponse findResultById(@PathVariable("id") Long id) {
        return resultService.findResultById(id);
    }

    @PutMapping("/{id}")
    public ResultResponse updateResult(@PathVariable("id") Long id,
                                       @RequestBody ResultRequest resultRequest) {
        return resultService.updateResult(id, resultRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteResult(@PathVariable("id") Long id) {
        resultService.deleteResult(id);
    }

    @GetMapping("/final/{id}")
    public FinalResultResponse findFinalResultById(@PathVariable Long id) {
        return resultService.findFinalResultById(id);
    }
}
