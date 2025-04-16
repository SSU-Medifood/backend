package Mefo.server.domain.allergyEtc.repository;

import Mefo.server.domain.allergyEtc.entity.AllergyEtc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllergyEtcRepository extends JpaRepository<AllergyEtc, Long> {
    List<AllergyEtc> findAllById(Iterable<Long> idList);
}
