package Mefo.server.domain.userInfo.service;

import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.service.UserService;
import Mefo.server.domain.userAllergyDrug.entity.UserAllergyDrug;
import Mefo.server.domain.userAllergyDrug.repository.UserAllergyDrugRepository;
import Mefo.server.domain.userAllergyEtc.entity.UserAllergyEtc;
import Mefo.server.domain.userAllergyEtc.repository.UserAllergyEtcRepository;
import Mefo.server.domain.userDisease.entity.UserDisease;
import Mefo.server.domain.userDisease.repository.UserDiseaseRepository;
import Mefo.server.domain.userInfo.dto.UserInfoPatchRequest;
import Mefo.server.domain.userInfo.dto.UserInfoRequest;
import Mefo.server.domain.userInfo.entity.UserInfo;
import Mefo.server.domain.userInfo.repository.UserInfoRepository;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final UserAllergyDrugRepository userAllergyDrugRepository;
    private final UserAllergyEtcRepository userAllergyEtcRepository;
    private final UserDiseaseRepository userDiseaseRepository;
    private final UserService userService;
    //userId로 userInfo 찾기
//    public UserInfo findByUserId(Long id){
//        return userInfoRepository.findByUserId(id)
//                .orElseThrow(()-> new BusinessException(ErrorCode.USER_DOESNT_EXIST));
//    }
    //authentication으로 userinfo 찾기
    public UserInfo findByLoginUser(String email){
        User user = userService.getLoginUser(email);
        return userInfoRepository.findByUserId(user.getId())
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_DOESNT_EXIST));
    }

    //유저 건강 정보 수정하기
    public UserInfo patchUserInfo(String email, UserInfoPatchRequest userInfoPatchRequest){
        UserInfo userInfo = findByLoginUser(email);
        if(userInfo.isAllergy()){
            userInfo.getUserAllergyDrugList().clear();
            userInfo.getUserAllergyEtcList().clear();
            userInfo.getUserDiseaseList().clear();
            userAllergyDrugRepository.deleteByUserInfo(userInfo);
            userAllergyEtcRepository.deleteByUserInfo(userInfo);
            userDiseaseRepository.deleteByUserInfo(userInfo);
        }
        if(userInfoPatchRequest.isAllergy()){
            List<UserAllergyDrug> userAllergyDrugs = userService.createUserAllergyDrugList(userInfo, userInfoPatchRequest.getAllergyDrugs());
            List<UserAllergyEtc> userAllergyEtcs = userService.createUserAllergyEtcList(userInfo, userInfoPatchRequest.getAllergyEtcs());
            List<UserDisease> userDiseases = userService.createUserDiseaseList(userInfo, userInfoPatchRequest.getDiseases());
        }
        userInfo.patchUserInfo(userInfoPatchRequest);
        userInfoRepository.save(userInfo);
        return userInfo;
    }
}
