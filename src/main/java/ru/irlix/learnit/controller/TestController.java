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
import ru.irlix.learnit.dto.request.TestRequest;
import ru.irlix.learnit.dto.response.test.TestFullResponse;
import ru.irlix.learnit.dto.response.test.TestResponse;
import ru.irlix.learnit.service.api.TestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    @PostMapping
    public TestFullResponse creteTest(@RequestBody TestRequest testRequest) {
        return testService.createTest(testRequest);
    }

    @GetMapping
    public List<TestResponse> findAllTests() {
        return testService.findAllTests();
    }

    @GetMapping("/{id}")
    public TestFullResponse findTestById(@PathVariable("id") Long id) {
        return testService.findTestById(id);
    }

    @PutMapping("/{id}")
    public TestFullResponse updateTest(@PathVariable("id") Long id, @RequestBody TestRequest testRequest) {
        return testService.updateTest(id, testRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable("id") Long id) {
        testService.deleteTest(id);
    }
}
