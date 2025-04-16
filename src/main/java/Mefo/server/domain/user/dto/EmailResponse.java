package Mefo.server.domain.user.dto;

import Mefo.server.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmailResponse {
    private Long userId;
    private String email;

    public static EmailResponse from(User user){
        return EmailResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }
}
