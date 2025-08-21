package Mefo.server.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeviceResponse {
    private String device;

    private boolean pushAlarm;
    public static DeviceResponse from(String device, boolean pushAlarm){
        return DeviceResponse.builder()
                .device(device)
                .pushAlarm(pushAlarm)
                .build();
    }
}
