package Mefo.server.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeviceResponse {
    private String device;

    private boolean pushAlarm;
    public static DeviceResponse from(DeviceRequest deviceRequest, boolean pushAlarm){
        return DeviceResponse.builder()
                .device(deviceRequest.getDevice())
                .pushAlarm(pushAlarm)
                .build();
    }
}
