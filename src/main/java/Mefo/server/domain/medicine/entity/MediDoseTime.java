package Mefo.server.domain.medicine.entity;

import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MediDoseTime {
    FAST("공복"),
    BEFORE("식전"),
    BETWEEN("식간"),
    AFTER("식후");

    private final String time;

    public String getDoseTime(){return  time;}
    @JsonCreator
    public static MediDoseTime from(String value){
        for(MediDoseTime time : MediDoseTime.values()){
            if(time.time.equals(value)){
                return time;
            }
        }
        throw new BusinessException(ErrorCode.INVALID_ENUM_VALUE);
    }
}
