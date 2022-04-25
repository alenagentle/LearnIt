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
import ru.irlix.learnit.dto.request.VariantRequest;
import ru.irlix.learnit.dto.response.variant.VariantResponse;
import ru.irlix.learnit.service.api.VariantService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/variant")
public class VariantController {

    private final VariantService variantService;

    @PostMapping
    public VariantResponse createVariant(@RequestBody VariantRequest variantRequest) {
        return variantService.createVariant(variantRequest);
    }

    @GetMapping("/{id}")
    public VariantResponse findVariantById(@PathVariable("id") Long id) {
        return variantService.findVariantById(id);
    }

    @GetMapping
    public List<VariantResponse> findVariantsByQuestionId(Long id) {
        return variantService.findVariantsByQuestionId(id);
    }

    @PutMapping("/{id}")
    public VariantResponse updateVariant(@PathVariable("id") Long id,
                                         @RequestBody VariantRequest variantRequest) {
        return variantService.updateVariant(id, variantRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteVariant(@PathVariable("id") Long id) {
        variantService.deleteVariant(id);
    }
}
