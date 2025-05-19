package Mefo.server.domain.medicine.entity;

import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MediPerOnce {
    ONE("1회 1정"),
    TWO("1회 2정"),
    THREE("1회 3정"),
    ETC("기타");

    private final String number;

    public String getPerOnce(){return number;}
    @JsonCreator
    public static MediPerOnce from(String value){
        for(MediPerOnce number : MediPerOnce.values()){
            if(number.number.equals(value)){
                return number;
            }
        }
        throw new BusinessException(ErrorCode.INVALID_ENUM_VALUE);
    }
}
