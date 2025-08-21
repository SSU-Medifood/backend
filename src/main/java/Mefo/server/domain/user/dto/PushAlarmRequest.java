package Mefo.server.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PushAlarmRequest {
    private String device;
    private String fcmToken;
}
