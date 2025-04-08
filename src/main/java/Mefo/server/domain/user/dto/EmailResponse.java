package Mefo.server.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmailResponse {
    private String email;

    public static EmailResponse from(String email){
        return EmailResponse.builder()
                .email(email)
                .build();
    }
}
