package ru.irlix.learnit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.irlix.learnit.entity.Variant;

import java.util.Collection;
import java.util.List;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {

    List<Variant> findVariantsByQuestionId(Long questionId);

    List<Variant> findVariantsByIsRightTrueAndQuestionId(Long questionId);

    List<Variant> findVariantsByQuestionIdAndIdIn(Long questionId, Collection<Long> id);
}