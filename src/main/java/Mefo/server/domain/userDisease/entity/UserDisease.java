package Mefo.server.domain.userDisease.entity;

import Mefo.server.domain.common.BaseEntity;
import Mefo.server.domain.disease.entity.Disease;
import Mefo.server.domain.userInfo.entity.UserInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class UserDisease extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userInfoId", nullable = false)
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diseaseId", nullable = false)
    private Disease disease;

    public UserDisease(UserInfo userInfo, Disease disease) {
        this.userInfo = userInfo;
        this.disease = disease;
    }
}
