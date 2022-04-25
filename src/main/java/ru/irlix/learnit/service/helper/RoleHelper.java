package ru.irlix.learnit.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.irlix.learnit.entity.Role;
import ru.irlix.learnit.exception.NotFoundException;
import ru.irlix.learnit.repository.RoleName;
import ru.irlix.learnit.repository.RoleRepository;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class RoleHelper {

    private final RoleRepository roleRepository;

    public Role findByName(RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new NotFoundException(String.format("Role '%s' is not found", roleName.name())));
    }
}
