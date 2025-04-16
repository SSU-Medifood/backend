package Mefo.server.domain.userAllergyDrug.repository;

import Mefo.server.domain.userAllergyDrug.entity.UserAllergyDrug;
import Mefo.server.domain.userInfo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAllergyDrugRepository extends JpaRepository<UserAllergyDrug, Long> {
    void deleteByUserInfo(UserInfo userInfo);
}
