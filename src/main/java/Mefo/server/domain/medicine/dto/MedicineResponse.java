package Mefo.server.domain.medicine.dto;

import Mefo.server.domain.alarm.dto.AlarmResponse;
import Mefo.server.domain.medicine.entity.Medicine;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class MedicineResponse {
    private Long id;
    private String mediName;
    private String mediNickName;
    private String mediPerOnce;
    private String mediDoseTime;
    private boolean alarm;
    private int perDay;
    private List<AlarmResponse> alarmTimes;

    public static List<MedicineResponse> from(List<Medicine> medicineList){
        List<MedicineResponse> medicineResponseList = new ArrayList<>();
        for(Medicine medicine : medicineList){
            MedicineResponse medicineResponse = MedicineResponse.builder()
                    .id(medicine.getId())
                    .mediName(medicine.getMediName())
                    .mediNickName(medicine.getMediNickName())
                    .mediPerOnce(medicine.getMediPerOnce().getPerOnce())
                    .mediDoseTime(medicine.getMediDoseTime().getDoseTime())
                    .alarm(medicine.isAlarm())
                    .perDay(medicine.getPerDay())
                    .alarmTimes(AlarmResponse.from(medicine.getAlarmTime()))
                    .build();
            medicineResponseList.add(medicineResponse);
        }
        return medicineResponseList;
    }
    public static MedicineResponse from(Medicine medicine){
        return MedicineResponse.builder()
                .id(medicine.getId())
                .mediName(medicine.getMediName())
                .mediNickName(medicine.getMediNickName())
                .mediPerOnce(medicine.getMediPerOnce().getPerOnce())
                .mediDoseTime(medicine.getMediDoseTime().getDoseTime())
                .alarm(medicine.isAlarm())
                .perDay(medicine.getPerDay())
                .alarmTimes(AlarmResponse.from(medicine.getAlarmTime()))
                .build();
    }
}
