package Mefo.server.domain.alarm.dto;

import Mefo.server.domain.alarm.entity.Alarm;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class AlarmResponse {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(type = "string", example = "08:00:00")
    private LocalTime alarmTime;

    public static List<AlarmResponse> from(List<Alarm> alarms){
        List<AlarmResponse> alarmResponses = new ArrayList<>();
        for(Alarm alarm : alarms){
            AlarmResponse alarmResponse = AlarmResponse.builder()
                    .id(alarm.getId())
                    .alarmTime(alarm.getAlarmTime())
                    .build();
            alarmResponses.add(alarmResponse);
        }
        return alarmResponses;
    }
}
