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
import Mefo.server.domain.userAllergyEtc.entity.UserAllergyEtc;
import Mefo.server.domain.userDisease.entity.UserDisease;
import Mefo.server.domain.userInfo.entity.UserInfo;
import Mefo.server.domain.userInfo.repository.UserInfoRepository;
import Mefo.server.domain.userInfo.service.UserInfoService;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
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

        userInfo.getUserAllergyDrugList().addAll(createUserAllergyDrugList(userInfo, joinRequest.getAllergyDrugs()));
        userInfo.getUserAllergyEtcList().addAll(createUserAllergyEtcList(userInfo, joinRequest.getAllergyEtcs()));
        userInfo.getUserDiseaseList().addAll(createUserDiseaseList(userInfo, joinRequest.getDiseases()));

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

    //유저 설정 변경
    @Transactional
    public User patchUser(String email, UserRequest userRequest){
        User user = getLoginUser(email);
        user.setPushAlarm(userRequest.isPushAlarm());
        user.setMarketing(userRequest.isMarketing());
        userRepository.save(user);
        return user;
    }

    //회원 탈퇴하기
    @Transactional
    public void deleteUser(String email){
        User user = getLoginUser(email);
        UserInfo userInfo = userInfoRepository.findByUserId(user.getId())
                        .orElseThrow(()-> new BusinessException(ErrorCode.USER_DOESNT_EXIST));
        userInfoRepository.delete(userInfo);
        userRepository.delete(user);
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

    public List<UserAllergyDrug> createUserAllergyDrugList(UserInfo userInfo, List<Long> allergyDrug) {
        List<AllergyDrug> allergyDrugs = allergyDrugRepository.findAllById(allergyDrug);
        List<UserAllergyDrug> userAllergyDrugs = allergyDrugs.stream()
                .map(drug -> new UserAllergyDrug(userInfo, drug))
                .collect(Collectors.toList());
        userAllergyDrugs.forEach(userInfo.getUserAllergyDrugList()::add);
        return userAllergyDrugs;
    }

    public List<UserAllergyEtc> createUserAllergyEtcList(UserInfo userInfo, List<Long> allergyEtc) {
        List<AllergyEtc> allergyEtcs = allergyEtcRepository.findAllById(allergyEtc);
        List<UserAllergyEtc> userAllergyEtcs = allergyEtcs.stream()
                .map(etc -> new UserAllergyEtc(userInfo, etc))
                .collect(Collectors.toList());
        userAllergyEtcs.forEach(userInfo.getUserAllergyEtcList()::add);
        return userAllergyEtcs;
    }

    public List<UserDisease> createUserDiseaseList(UserInfo userInfo, List<Long> disease) {
        List<Disease> diseases = diseaseRepository.findAllById(disease);
        List<UserDisease> userDiseases = diseases.stream()
                .map(d -> new UserDisease(userInfo, d))
                .collect(Collectors.toList());
        userDiseases.forEach(userInfo.getUserDiseaseList()::add);
        return userDiseases;
    }

    //이메일로 유저 찾기
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_DOESNT_EXIST));
    }
    //id로 유저 찾기
    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_DOESNT_EXIST));
    }
}
