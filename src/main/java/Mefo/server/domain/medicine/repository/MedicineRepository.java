package Mefo.server.domain.medicine.repository;

import Mefo.server.domain.medicine.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findAllByUserId(Long id);
    Optional<Medicine> findByIdAndUserId(Long id, Long userId);
}
