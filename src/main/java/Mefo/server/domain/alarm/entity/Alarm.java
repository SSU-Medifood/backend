package Mefo.server.domain.alarm.entity;

import Mefo.server.domain.common.BaseEntity;
import Mefo.server.domain.medicine.entity.Medicine;
import Mefo.server.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mediId")
    private Medicine medicine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(type = "string", example = "08:00:00")
    private LocalTime alarmTime;

    public Alarm(Medicine medicine, User user, LocalTime alarmTime){
        this.medicine = medicine;
        this.user = user;
        this.alarmTime = alarmTime;
    }

}
