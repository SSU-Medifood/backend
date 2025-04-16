package Mefo.server.domain.userInfo.service;

import Mefo.server.domain.user.service.UserService;
import Mefo.server.domain.userAllergyDrug.repository.UserAllergyDrugRepository;
import Mefo.server.domain.userAllergyEtc.repository.UserAllergyEtcRepository;
import Mefo.server.domain.userDisease.repository.UserDiseaseRepository;
import Mefo.server.domain.userInfo.dto.UserInfoPatchRequest;
import Mefo.server.domain.userInfo.dto.UserInfoRequest;
import Mefo.server.domain.userInfo.entity.UserInfo;
import Mefo.server.domain.userInfo.repository.UserInfoRepository;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final UserAllergyDrugRepository userAllergyDrugRepository;
    private final UserAllergyEtcRepository userAllergyEtcRepository;
    private final UserDiseaseRepository userDiseaseRepository;
    private final UserService userService;
    //userId로 userInfo 찾기
    public UserInfo findByUserId(Long id){
        return userInfoRepository.findByUserId(id)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_DOESNT_EXIST));
    }

    //유저 건강 정보 수정하기
    public UserInfo patchUserInfo(Long userId, UserInfoPatchRequest userInfoPatchRequest){
        UserInfo userInfo = findByUserId(userId);
        userInfo.setUserSex(userInfoPatchRequest.getUserSex());
        userInfo.setHeight(userInfoPatchRequest.getHeight());
        userInfo.setWeight(userInfoPatchRequest.getWeight());
        userInfo.setUserSmoke(userInfoPatchRequest.getUserSmoke());
        userInfo.setUserDrink(userInfoPatchRequest.getUserDrink());
        userInfo.setAllergy(userInfoPatchRequest.isAllergy());
        userAllergyDrugRepository.deleteByUserInfo(userInfo);
        userAllergyEtcRepository.deleteByUserInfo(userInfo);
        userDiseaseRepository.deleteByUserInfo(userInfo);
        userInfo.getUserAllergyDrugList().clear();
        userInfo.getUserAllergyEtcList().clear();
        userInfo.getUserDiseaseList().clear();
        userInfo.getUserAllergyDrugList().addAll(userService.createUserAllergyDrugList(userInfo, userInfoPatchRequest.getAllergyDrugs()));
        userInfo.getUserAllergyEtcList().addAll(userService.createUserAllergyEtcList(userInfo, userInfoPatchRequest.getAllergyEtcs()));
        userInfo.getUserDiseaseList().addAll(userService.createUserDiseaseList(userInfo, userInfoPatchRequest.getDiseases()));
        userInfoRepository.save(userInfo);
        return userInfo;
    }
}
