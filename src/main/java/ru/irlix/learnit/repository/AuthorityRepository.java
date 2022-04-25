package ru.irlix.learnit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.irlix.learnit.entity.Authority;
import ru.irlix.learnit.entity.Role;

import java.util.Collection;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Collection<Authority> findAuthoritiesByRolesIn(Collection<Role> roles);
}
