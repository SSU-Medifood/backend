package Mefo.server.domain.userAllergyDrug.entity;

import Mefo.server.domain.allergyDrug.entity.AllergyDrug;
import Mefo.server.domain.common.BaseEntity;
import Mefo.server.domain.userInfo.entity.UserInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserAllergyDrug extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userInfoId", nullable = false)
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allergyDrugId", nullable = false)
    private AllergyDrug allergyDrug;

    public UserAllergyDrug(UserInfo userInfo, AllergyDrug allergyDrug) {
        this.userInfo = userInfo;
        this.allergyDrug = allergyDrug;
    }

}
