package Mefo.server.domain.medicine.service;

import Mefo.server.domain.alarm.entity.Alarm;
import Mefo.server.domain.alarm.repository.AlarmRepository;
import Mefo.server.domain.medicine.dto.MedicineRequest;
import Mefo.server.domain.medicine.entity.Medicine;
import Mefo.server.domain.medicine.repository.MedicineRepository;
import Mefo.server.domain.user.entity.User;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import Mefo.server.global.scheduler.component.AlarmComponent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final AlarmRepository alarmRepository;

    private final AlarmComponent alarmComponent;

    //복용약 등록하기
    @Transactional
    public Medicine createMedicine(User user, MedicineRequest medicineRequest){
        Medicine medicine = new Medicine(user, medicineRequest);
        user.getMedicines().add(medicine);
        if(medicineRequest.isAlarm()){
            createAlarm(medicine, user, medicineRequest.getAlarmTime());
        }
        medicineRepository.save(medicine);
        return medicine;
    }
    //약 정보 수정하기
    @Transactional
    public Medicine patchMedicine(User user, Long mediId, MedicineRequest medicineRequest){
        Medicine medicine = medicineRepository.findByIdAndUserId(mediId, user.getId())
                .orElseThrow(()-> new BusinessException(ErrorCode.MEDICINE_DOESNT_EXIST));
        if(medicine.isAlarm()){
            for(Alarm alarm : medicine.getAlarmTime()){
                alarmComponent.cancelMediAlarm(alarm.getId());
            }
            medicine.getAlarmTime().clear();
            alarmRepository.deleteByMedicine(medicine);
        }
        if(medicineRequest.isAlarm()){
            createAlarm(medicine, user, medicineRequest.getAlarmTime());
        }
        medicine.patchMedicine(medicineRequest);
        medicineRepository.save(medicine);
        return medicine;
    }

    //복용약 삭제하기
    @Transactional
    public void deleteMedicine(User user, Long mediId){
        Medicine medicine = medicineRepository.findByIdAndUserId(mediId, user.getId())
                .orElseThrow(()-> new BusinessException(ErrorCode.MEDICINE_DOESNT_EXIST));
        user.getMedicines().remove(medicine);
        if(medicine.isAlarm()){
            for(Alarm alarm : medicine.getAlarmTime()){
                alarmComponent.cancelMediAlarm(alarm.getId());
            }
        }
        medicineRepository.delete(medicine);
    }


    //LocalTime List 받아서 alarm 객체 만들기
    public void createAlarm(Medicine medicine, User user, List<LocalTime> times){
        List<Alarm> alarms = new ArrayList<>();
        for (LocalTime time : times) {
            Alarm alarm = new Alarm(medicine, user, time);
            alarmRepository.save(alarm);
            alarmComponent.scheduleMediAlarm(user, alarm);
            alarms.add(alarm);
        }
        medicine.getAlarmTime().addAll(alarms);
    }
}
