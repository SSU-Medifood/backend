package Mefo.server.domain.user.service;

import Mefo.server.domain.allergyDrug.entity.AllergyDrug;
import Mefo.server.domain.allergyDrug.repository.AllergyDrugRepository;
import Mefo.server.domain.allergyEtc.entity.AllergyEtc;
import Mefo.server.domain.allergyEtc.repository.AllergyEtcRepository;
import Mefo.server.domain.disease.entity.Disease;
import Mefo.server.domain.disease.repository.DiseaseRepository;
import Mefo.server.domain.storage.entity.Storage;
import Mefo.server.domain.user.dto.*;
import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.repository.UserRepository;
import Mefo.server.domain.userAllergyDrug.entity.UserAllergyDrug;
import Mefo.server.domain.userAllergyDrug.repository.UserAllergyDrugRepository;
import Mefo.server.domain.userAllergyEtc.entity.UserAllergyEtc;
import Mefo.server.domain.userDisease.entity.UserDisease;
import Mefo.server.domain.userInfo.entity.UserInfo;
import Mefo.server.domain.userInfo.repository.UserInfoRepository;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AllergyDrugRepository allergyDrugRepository;
    private final AllergyEtcRepository allergyEtcRepository;
    private final DiseaseRepository diseaseRepository;

    //이메일 중복 확인하기
    public EmailCheckResponse checkEmailDuplicate(EmailCheckRequest emailCheckRequest){
        return EmailCheckResponse.from(userRepository.findByEmail(emailCheckRequest.getEmail()).isPresent());
    }

    //회원가입 하기
    @Transactional
    public User join(JoinRequest joinRequest){
        joinRequest.setPassword(bCryptPasswordEncoder.encode(joinRequest.getPassword()));
        User user = joinRequest.toUserEntity();
        UserInfo userInfo = joinRequest.toUserInfoEntity(user);

        List<AllergyDrug> allergyDrugList = allergyDrugRepository.findAllById(joinRequest.getAllergyDrugs());
        userInfo.getUserAllergyDrugList().addAll(createUserAllergyDrugList(userInfo, allergyDrugList));

        List<AllergyEtc> allergyEtcList = allergyEtcRepository.findAllById(joinRequest.getAllergyEtcs());
        userInfo.getUserAllergyEtcList().addAll(createUserAllergyEtcList(userInfo, allergyEtcList));

        List<Disease> diseaseList = diseaseRepository.findAllById(joinRequest.getDiseases());
        userInfo.getUserDiseaseList().addAll(createUserDiseaseList(userInfo, diseaseList));

        Storage storage = new Storage(user, "전체 보관함");
        user.getStorage().add(storage);

        userRepository.save(user);
        userInfoRepository.save(userInfo);

        return user;

    }

    //비밀번호 변경
    @Transactional
    public void changePassword(PasswordRequest passwordRequest){
        User user = getUserByEmail(passwordRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(passwordRequest.getPassword()));
        userRepository.save(user);
    }

//    //로그인 하기
//    public User login(LoginRequest loginRequest){
//        User user = userRepository.findByEmail(loginRequest.getEmail())
//                .orElseThrow(() -> new BusinessException(ErrorCode.USER_DOESNT_EXIST));
////        if(!user.getPassword().equals(loginRequest.getEmail())) {
////            throw new BusinessException(ErrorCode.EMAIL_PASSWORD_DOESNT_MATCH);
////        }
//        return user;
//    }

    //현재 로그인 한 계정(이메일)
    public User getLoginUser(String email){
        return getUserByEmail(email);
    }

    private List<UserAllergyDrug> createUserAllergyDrugList(UserInfo userInfo, List<AllergyDrug> allergyDrugs) {
        return allergyDrugs.stream()
                .map(drug -> new UserAllergyDrug(userInfo, drug))
                .collect(Collectors.toList());
    }

    private List<UserAllergyEtc> createUserAllergyEtcList(UserInfo userInfo, List<AllergyEtc> allergyEtcs) {
        return allergyEtcs.stream()
                .map(etc -> new UserAllergyEtc(userInfo, etc))
                .collect(Collectors.toList());
    }

    private List<UserDisease> createUserDiseaseList(UserInfo userInfo, List<Disease> diseases) {
        return diseases.stream()
                .map(d -> new UserDisease(userInfo, d))
                .collect(Collectors.toList());
    }

    //이메일로 유저 찾기
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_DOESNT_EXIST));
    }
}
