package Mefo.server.domain.user.entity;


import Mefo.server.domain.common.BaseEntity;
import Mefo.server.domain.medicine.entity.Medicine;
import Mefo.server.domain.storage.entity.Storage;
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
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserState userState;

    @NotNull
    private boolean pushAlarm;

    @NotNull
    private boolean marketing;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Storage> userStorage = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Medicine> userMedicine = new ArrayList<>();
}
