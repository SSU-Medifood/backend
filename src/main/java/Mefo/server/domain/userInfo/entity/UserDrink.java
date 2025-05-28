package Mefo.server.domain.userInfo.entity;

import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserDrink {
    RARELY("0~1회"),
    SOMETIMES("2~3회"),
    OFTEN("4~5회"),
    ALWAYS("6~7회");

    private final String times;

    public String getDrinkTimes(){
        return times;
    }
    @JsonCreator
    public static UserDrink from(String value) {
        for (UserDrink times : UserDrink.values()) {
            if (times.times.equals(value)) {
                return times;
            }
        }
        throw new BusinessException(ErrorCode.INVALID_ENUM_VALUE);
    }
}
