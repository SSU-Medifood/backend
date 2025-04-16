package Mefo.server.domain.userDisease.repository;

import Mefo.server.domain.userDisease.entity.UserDisease;
import Mefo.server.domain.userInfo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDiseaseRepository extends JpaRepository<UserDisease, Long> {
    void deleteByUserInfo(UserInfo userInfo);
}
