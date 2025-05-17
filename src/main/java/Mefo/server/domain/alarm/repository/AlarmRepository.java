package Mefo.server.domain.alarm.repository;

import Mefo.server.domain.alarm.entity.Alarm;
import Mefo.server.domain.medicine.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    void deleteByMedicine(Medicine medicine);
}
