package Mefo.server.domain.user.dto;

import Mefo.server.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SettingResponse {
    private boolean pushAlarm;
    private boolean marketing;

    public static SettingResponse from(User user, boolean pushAlarm){
        return SettingResponse.builder()
                .pushAlarm(pushAlarm)
                .marketing(user.isMarketing())
                .build();
    }
}
