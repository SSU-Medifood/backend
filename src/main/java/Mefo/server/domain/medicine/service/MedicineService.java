package Mefo.server.domain.medicine.service;

import Mefo.server.domain.alarm.entity.Alarm;
import Mefo.server.domain.alarm.repository.AlarmRepository;
import Mefo.server.domain.medicine.dto.MedicineRequest;
import Mefo.server.domain.medicine.entity.Medicine;
import Mefo.server.domain.medicine.repository.MedicineRepository;
import Mefo.server.domain.user.entity.User;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final AlarmRepository alarmRepository;

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
        Medicine medicine = checkMedicineUser(user, mediId);
        if(medicine.isAlarm()){
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
        Medicine medicine = checkMedicineUser(user, mediId);
        medicineRepository.delete(medicine);
    }


    //medi_Id가 요청한 사용자의 것이 맞는지 확인 후 Medicine 반환
    public Medicine checkMedicineUser(User user, Long mediId){
        Medicine medicine = medicineRepository.findById(mediId)
                .orElseThrow(()-> new BusinessException(ErrorCode.MEDICINE_DOESNT_EXIST));
        if(!medicine.getUser().getId().equals(user.getId())){
            throw new BusinessException(ErrorCode.NOT_VALID_ACCESS);
        }
        return medicine;
    }

    //LocalTime List 받아서 alarm 객체 만들기
    public void createAlarm(Medicine medicine, User user, List<LocalTime> times){
        List<Alarm> alarms = new ArrayList<>();
        for (LocalTime time : times) {
            Alarm alarm = new Alarm(medicine, user, time);
            alarms.add(alarm);
        }
        medicine.getAlarmTime().addAll(alarms);
    }
}
