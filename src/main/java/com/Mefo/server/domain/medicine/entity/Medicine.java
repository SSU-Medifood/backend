package com.Mefo.server.domain.medicine.entity;

import com.Mefo.server.domain.common.BaseEntity;
import com.Mefo.server.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Medicine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @NotNull
    private String mediName;

    @NotNull
    private String mediNickName;

    @NotNull
    private int perDay;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MediPerOnce mediPerOnce;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MediDoseTime mediDoseTime;

    @NotNull
    private boolean alarm;

    private List<LocalTime> alarmTime;
}
