package Mefo.server.domain.userInfo.entity;

import Mefo.server.domain.common.BaseEntity;
import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.userAllergyDrug.entity.UserAllergyDrug;
import Mefo.server.domain.userAllergyEtc.entity.UserAllergyEtc;
import Mefo.server.domain.userDisease.entity.UserDisease;
import Mefo.server.domain.userInfo.dto.UserInfoPatchRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", unique = true, nullable = false)
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
    private float weight;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserSmoke userSmoke;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserDrink userDrink;

    @NotNull
    private boolean allergy;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAllergyDrug> userAllergyDrugList;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAllergyEtc> userAllergyEtcList;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserDisease> userDiseaseList;

    public void patchUserInfo(UserInfoPatchRequest userInfoPatchRequest){
        this.userSex = userInfoPatchRequest.getUserSex();
        this.height = userInfoPatchRequest.getHeight();
        this.weight = userInfoPatchRequest.getWeight();
        this.userSmoke = userInfoPatchRequest.getUserSmoke();
        this.userDrink = userInfoPatchRequest.getUserDrink();
        this.allergy = userInfoPatchRequest.isAllergy();
    }
}
