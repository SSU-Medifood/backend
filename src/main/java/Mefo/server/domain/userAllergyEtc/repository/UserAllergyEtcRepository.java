package Mefo.server.domain.userAllergyEtc.repository;

import Mefo.server.domain.userAllergyEtc.entity.UserAllergyEtc;
import Mefo.server.domain.userInfo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAllergyEtcRepository extends JpaRepository<UserAllergyEtc, Long> {
    void deleteByUserInfo(UserInfo userInfo);
}
