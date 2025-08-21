package Mefo.server.global.firebase.entity;

import Mefo.server.domain.common.BaseEntity;
import Mefo.server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FirebaseToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private String device;

    private String fcmToken;

    public FirebaseToken(User user, String device, String fcmToken){
        this.user = user;
        this.device = device;
        this.fcmToken = fcmToken;
    }

    public void setFcmToken(String fcmToken){
        this.fcmToken = fcmToken;
    }
}

