package Mefo.server.domain.userAllergyEtc.entity;

import Mefo.server.domain.allergyEtc.entity.AllergyEtc;
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
public class UserAllergyEtc extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userInfoId", nullable = false)
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allergyEtcId", nullable = false)
    private AllergyEtc allergyEtc;

    public UserAllergyEtc(UserInfo userInfo, AllergyEtc allergyEtc) {
        this.userInfo = userInfo;
        this.allergyEtc = allergyEtc;
    }
}
