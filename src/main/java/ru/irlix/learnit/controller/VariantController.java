package ru.irlix.learnit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
import ru.irlix.learnit.dto.request.VariantRequest;
import ru.irlix.learnit.dto.response.variant.VariantResponse;
import ru.irlix.learnit.service.api.VariantService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/variant")
@Validated
@CrossOrigin
public class VariantController {

    private final VariantService variantService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @PostMapping
    @Validated(OnCreateGroup.class)
    public VariantResponse createVariant(@RequestBody @Valid VariantRequest request) {
        return variantService.createVariant(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @PutMapping("/{id}")
    @Validated(OnUpdateGroup.class)
    public VariantResponse updateVariant(@PathVariable
                                         @Positive(groups = OnUpdateGroup.class,
                                                 message = "{id.positive}") Long id,
                                         @RequestBody @Valid VariantRequest request) {
        return variantService.updateVariant(id, request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @DeleteMapping("/{id}")
    public void deleteVariant(@PathVariable @Positive(message = "{id.positive}") Long id) {
        variantService.deleteVariant(id);
    }

    @GetMapping("/{id}")
    public VariantResponse findVariantById(@PathVariable @Positive(message = "{id.positive}") Long id) {
        return variantService.findVariantById(id);
    }

    @GetMapping
    public List<VariantResponse> findVariantsByQuestionId(@Positive(message = "{id.positive}") Long questionId) {
        return variantService.findVariantsByQuestionId(questionId);
    }
}
