package Mefo.server.global.mail.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthCodeResponse {
    private String email;
    private String authCode;

    public static AuthCodeResponse from(String email, String authCode){
        return AuthCodeResponse.builder()
                .email(email)
                .authCode(authCode)
                .build();
    }
}
