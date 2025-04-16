package Mefo.server.global.login.dto;

import Mefo.server.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenResponse {
    private String email;
    private String token;
    public static TokenResponse from(String email, String token){
        return TokenResponse.builder()
                .email(email)
                .token(token)
                .build();
    }
}
