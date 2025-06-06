package Mefo.server.domain.user.entity;


import Mefo.server.domain.common.BaseEntity;
import Mefo.server.domain.medicine.entity.Medicine;
import Mefo.server.domain.storage.entity.Storage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Storage> storages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medicine> medicines;

    public void setEmail(String email){this.email = email;}
    public void setPassword(String password){this.password = password;}
    public void setUserState(UserState userState){this.userState = userState;}
    public void setMarketing(boolean agree){this.marketing = agree;}

    public void setPushAlarm(boolean agree){this.pushAlarm = agree;}
    public void setUserRole(UserRole userRole){this.userRole = userRole;}
}
