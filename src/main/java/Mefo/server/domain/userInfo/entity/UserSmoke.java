package Mefo.server.domain.userInfo.entity;

import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserSmoke {
    NO("아니요"),
    YES("예"),
    QUIT("끊음");

    private final String smoke;

    public String getSmoke(){
        return smoke;
    }
    @JsonCreator
    public static UserSmoke from(String value) {
        for (UserSmoke smoke : UserSmoke.values()) {
            if (smoke.smoke.equals(value)) {
                return smoke;
            }
        }
        throw new BusinessException(ErrorCode.INVALID_ENUM_VALUE);
    }
}
