package Mefo.server.domain.medicine.entity;

import Mefo.server.domain.alarm.entity.Alarm;
import Mefo.server.domain.common.BaseEntity;
import Mefo.server.domain.medicine.dto.MedicineRequest;
import Mefo.server.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alarm> alarmTime = new ArrayList<>();

    public Medicine(User user, MedicineRequest medicineRequest){
        this.user = user;
        this.mediName = medicineRequest.getMediName();
        this.mediNickName = medicineRequest.getMediNickName();
        this.mediPerOnce = medicineRequest.getMediPerOnce();
        this.mediDoseTime = medicineRequest.getMediDoseTime();
        this.alarm = medicineRequest.isAlarm();
        this.perDay = medicineRequest.getPerDay();
    }
    public void patchMedicine(MedicineRequest medicineRequest){
        this.mediName = medicineRequest.getMediName();
        this.mediNickName = medicineRequest.getMediNickName();
        this.mediPerOnce = medicineRequest.getMediPerOnce();
        this.mediDoseTime = medicineRequest.getMediDoseTime();
        this.alarm = medicineRequest.isAlarm();
        this.perDay = medicineRequest.getPerDay();
    }
}
