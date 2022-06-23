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
import ru.irlix.learnit.dto.request.ResultRequest;
import ru.irlix.learnit.dto.response.result.FinalResultResponse;
import ru.irlix.learnit.dto.response.result.ReportResponse;
import ru.irlix.learnit.dto.response.result.ResultFullResponse;
import ru.irlix.learnit.dto.response.result.ResultResponse;
import ru.irlix.learnit.dto.response.result.UsersResultResponse;
import ru.irlix.learnit.service.api.ResultService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/result")
@Validated
@CrossOrigin
public class ResultController {

    private final ResultService resultService;

    @PostMapping
    @Validated(OnCreateGroup.class)
    public ResultFullResponse createResult(@RequestBody @Valid ResultRequest resultRequest) {
        return resultService.createResult(resultRequest);
    }

    @PutMapping("/{id}")
    @Validated(OnUpdateGroup.class)
    public ResultFullResponse updateResult(@PathVariable
                                           @Positive(groups = OnUpdateGroup.class,
                                                   message = "{id.positive}") Long id,
                                           @RequestBody @Valid ResultRequest resultRequest) {
        return resultService.updateResult(id, resultRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteResult(@PathVariable @Positive(message = "{id.positive}") Long id) {
        resultService.deleteResult(id);
    }

    @GetMapping("/{id}")
    public ResultFullResponse findResultById(@PathVariable @Positive(message = "{id.positive}") Long id) {
        return resultService.findResultById(id);
    }

    @GetMapping("/test/{id}")
    public List<ResultResponse> findResultByTestId(@PathVariable @Positive(message = "{id.positive}") Long id) {
        return resultService.findResultByTestId(id);
    }

    @GetMapping("/final/{id}")
    public FinalResultResponse findFinalResultById(@PathVariable @Positive(message = "{id.positive}") Long id) {
        return resultService.findFinalResultById(id);
    }

    @GetMapping("/user/{name}")
    public UsersResultResponse findResultsByUsername(@PathVariable
                                                     @Size(min = 4, max = 320, message = "{login.size}") String name) {
        return resultService.findResultsByUsername(name);
    }

    @GetMapping(value = "/{id}/report")
    public ReportResponse getResultReport(@PathVariable Long id) {
        return resultService.getReport(id);
    }
}
