package Mefo.server.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailCheckResponse {
    private String message;

    public static EmailCheckResponse from(boolean check){
        String message;
        if(check){
            message="이미 가입한 이메일입니다.";
        }
        else{
            message="사용 가능한 이메일입니다.";
        }
        return EmailCheckResponse.builder()
                .message(message)
                .build();
    }
}
