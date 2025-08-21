package Mefo.server.domain.user.service;

import Mefo.server.domain.alarm.entity.Alarm;
import Mefo.server.domain.allergyDrug.entity.AllergyDrug;
import Mefo.server.domain.allergyDrug.repository.AllergyDrugRepository;
import Mefo.server.domain.allergyEtc.entity.AllergyEtc;
import Mefo.server.domain.allergyEtc.repository.AllergyEtcRepository;
import Mefo.server.domain.disease.entity.Disease;
import Mefo.server.domain.disease.repository.DiseaseRepository;
import Mefo.server.domain.medicine.entity.Medicine;
import Mefo.server.domain.medicine.repository.MedicineRepository;
import Mefo.server.domain.user.dto.*;
import Mefo.server.domain.user.entity.User;
import Mefo.server.domain.user.repository.UserRepository;
import Mefo.server.domain.userAllergyDrug.entity.UserAllergyDrug;
import Mefo.server.domain.userAllergyEtc.entity.UserAllergyEtc;
import Mefo.server.domain.userDisease.entity.UserDisease;
import Mefo.server.domain.userInfo.entity.UserInfo;
import Mefo.server.domain.userInfo.repository.UserInfoRepository;
import Mefo.server.domain.userRecipe.entity.UserRecipe;
import Mefo.server.domain.userRecipe.repository.UserRecipeRepository;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import Mefo.server.global.firebase.dto.TokenRequest;
import Mefo.server.global.firebase.entity.FirebaseToken;
import Mefo.server.global.firebase.repository.FirebaseRepository;
import Mefo.server.global.firebase.service.FirebaseService;
import Mefo.server.global.scheduler.component.AlarmComponent;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AllergyDrugRepository allergyDrugRepository;
    private final AllergyEtcRepository allergyEtcRepository;
    private final DiseaseRepository diseaseRepository;
    private final MedicineRepository medicineRepository;
    private final AlarmComponent alarmComponent;
    private final FirebaseRepository firebaseRepository;
    private final FirebaseService firebaseService;
    private final UserRecipeRepository userRecipeRepository;

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
        if(joinRequest.isAllergy()){
            userInfo.getUserAllergyDrugList().addAll(createUserAllergyDrugList(userInfo, joinRequest.getAllergyDrugs()));
            userInfo.getUserAllergyEtcList().addAll(createUserAllergyEtcList(userInfo, joinRequest.getAllergyEtcs()));
            userInfo.getUserDiseaseList().addAll(createUserDiseaseList(userInfo, joinRequest.getDiseases()));
        }

        userRepository.save(user);
        userInfoRepository.save(userInfo);

        return user;

    }

    //비밀번호 변경
    @Transactional
    public void patchPassword(PasswordRequest passwordRequest){
        User user = getUserByEmail(passwordRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(passwordRequest.getPassword()));
        userRepository.save(user);
    }

    //사용자 설정 불러오기
    public boolean getPushAlarm(User user, DeviceRequest deviceRequest){
        Optional<FirebaseToken> firebaseToken = firebaseRepository.findByUserIdAndDevice(user.getId(), deviceRequest.getDevice());
        boolean pushAlarm;
        if(firebaseToken.isPresent()){
            pushAlarm = true;
        }
        else{
            pushAlarm = false;
        }
        return pushAlarm;
    }


    //현재 접속한 기기의 푸시 알림 설정 변경
    @Transactional
    public boolean patchPushAlarm(String email, PushAlarmRequest pushAlarmRequest){
        User user = getLoginUser(email);
        Optional<FirebaseToken> firebaseToken = firebaseRepository.findByUserIdAndDevice(user.getId(), pushAlarmRequest.getDevice());
        boolean pushAlarm;
        if(firebaseToken.isPresent()){
            firebaseRepository.delete(firebaseToken.get());
            pushAlarm = false;
        }
        else{
            FirebaseToken token = new FirebaseToken(user, pushAlarmRequest.getDevice(), pushAlarmRequest.getFcmToken());
            firebaseRepository.save(token);
            pushAlarm = true;
        }
        return pushAlarm;
    }

    //유저 마케팅 수신 설정 변경
    @Transactional
    public User patchMarketing(String email, MarketingRequest marketingRequest){
        User user = getLoginUser(email);
        user.setMarketing(marketingRequest.isMarketing());
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
        List<FirebaseToken> firebaseTokens = firebaseRepository.findAllByUserId(user.getId());
        firebaseRepository.deleteAll(firebaseTokens);

        List<Medicine> medicines = medicineRepository.findAllByUserId(user.getId());
        for(Medicine medicine : medicines){
            for(Alarm alarm : medicine.getAlarmTime()) {
                alarmComponent.cancelMediAlarm(alarm.getId());
            }
        }
        List<UserRecipe> userRecipes = userRecipeRepository.findAllByUserId(user.getId());
        userRecipeRepository.deleteAll(userRecipes);
        userRepository.delete(user);
    }

    //현재 로그인 한 계정(이메일)
    public User getLoginUser(String email){
        return getUserByEmail(email);
    }

    public List<UserAllergyDrug> createUserAllergyDrugList(UserInfo userInfo, List<Long> allergyDrug) {
        List<AllergyDrug> allergyDrugs = allergyDrugRepository.findAllById(allergyDrug);
        List<UserAllergyDrug> userAllergyDrugs = new ArrayList<>();
        for (AllergyDrug drug : allergyDrugs) {
            UserAllergyDrug userAllergyDrug = new UserAllergyDrug(userInfo, drug);
            userAllergyDrugs.add(userAllergyDrug);
        }
        userInfo.getUserAllergyDrugList().addAll(userAllergyDrugs);
        return userAllergyDrugs;
    }

    public List<UserAllergyEtc> createUserAllergyEtcList(UserInfo userInfo, List<Long> allergyEtc) {
        List<AllergyEtc> allergyEtcs = allergyEtcRepository.findAllById(allergyEtc);
        List<UserAllergyEtc> userAllergyEtcs = new ArrayList<>();
        for (AllergyEtc etc : allergyEtcs) {
            UserAllergyEtc userAllergyEtc = new UserAllergyEtc(userInfo, etc);
            userAllergyEtcs.add(userAllergyEtc);
        }
        userInfo.getUserAllergyEtcList().addAll(userAllergyEtcs);
        return userAllergyEtcs;
    }

    public List<UserDisease> createUserDiseaseList(UserInfo userInfo, List<Long> disease) {
        List<Disease> diseases = diseaseRepository.findAllById(disease);
        List<UserDisease> userDiseases = new ArrayList<>();
        for (Disease d : diseases) {
            UserDisease userDisease = new UserDisease(userInfo, d);
            userDiseases.add(userDisease);
        }
        userInfo.getUserDiseaseList().addAll(userDiseases);
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
