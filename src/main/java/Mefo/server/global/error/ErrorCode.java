package Mefo.server.global.error;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //USER
    USER_DOESNT_EXIST(404, "존재하지 않는 회원입니다."),

    EMAIL_PASSWORD_DOESNT_MATCH(401, "이메일과 비밀번호가 일치하지 않습니다."),

    //TOKEN
    TOKEN_NOT_PROVIDED(401, "토큰이 없거나 형식이 잘못되었습니다."),

    TOKEN_EXPIRED(401, "만료된 토큰입니다."),

    //MEDICINE
    MEDICINE_DOESNT_EXIST(404, "존재하지 않는 복용약입니다."),

    //STORAGE
    STORAGE_DOESNT_EXIST(404, "존재하지 않는 보관함입니다."),

    //RECIPE_IMAGE
    IMAGE_DOESNT_EXIST(404, "존재하지 않는 레시피 이미지입니다."),

    //NOT_VALID
    NOT_VALID_ERROR(400, "잘못된 파라미터입니다."),

    NOT_VALID_ACCESS(400, "올바르지 않은 접근입니다."),

    INVALID_ENUM_VALUE(400, "올바르지 않은 값을 입력했습니다.");
    private final int status;
    private final String message;

}
