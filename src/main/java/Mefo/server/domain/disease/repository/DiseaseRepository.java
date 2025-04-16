package Mefo.server.domain.disease.repository;

import Mefo.server.domain.disease.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    List<Disease> findAllById(Iterable<Long> idList);
}
