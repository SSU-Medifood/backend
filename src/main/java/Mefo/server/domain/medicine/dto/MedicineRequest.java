package Mefo.server.domain.medicine.dto;

import Mefo.server.domain.medicine.entity.MediDoseTime;
import Mefo.server.domain.medicine.entity.MediPerOnce;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MedicineRequest {
    private String mediName;
    private String mediNickName;
    private MediPerOnce mediPerOnce;
    private MediDoseTime mediDoseTime;
    private boolean alarm;
    private int perDay;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(type = "string", example = "['08:00:00', '12:00:00']")
    private List<LocalTime> alarmTime;
}
