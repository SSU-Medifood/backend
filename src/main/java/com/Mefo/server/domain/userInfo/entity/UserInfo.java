package com.Mefo.server.domain.userInfo.entity;

import com.Mefo.server.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", unique = true)
    private User user;

    @NotNull
    private String name;

    @NotNull
    private String birth;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserSex userSex;

    @NotNull
    private float height;

    @NotNull
    private boolean smoke;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserDrink userDrink;

    @NotNull
    private boolean allergy;

    private List<String> allergyMedi;

    private List<String> allergyEtc;

    private List<String> disease;
}
