package Mefo.server.domain.allergyDrug.repository;

import Mefo.server.domain.allergyDrug.entity.AllergyDrug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllergyDrugRepository extends JpaRepository<AllergyDrug, Long> {
    List<AllergyDrug> findAllById(Iterable<Long> idList);
}
