package Mefo.server.domain.user.dto;

import Mefo.server.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {
    private boolean pushAlarm;
    private boolean marketing;

    public static UserResponse from(User user){
        return UserResponse.builder()
                .pushAlarm(user.isPushAlarm())
                .marketing(user.isMarketing())
                .build();
    }
}
