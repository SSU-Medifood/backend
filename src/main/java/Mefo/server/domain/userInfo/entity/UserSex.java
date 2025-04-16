package Mefo.server.domain.userInfo.entity;

import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserSex {
    MALE("남성"),
    FEMALE("여성");

    private final String sex;

    public String getSex(){
        return sex;
    }
    @JsonCreator
    public static UserSex from(String value) {
        for (UserSex sex : UserSex.values()) {
            if (sex.sex.equals(value)) {
                return sex;
            }
        }
        throw new BusinessException(ErrorCode.INVALID_ENUM_VALUE);
    }
}
