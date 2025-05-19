package Mefo.server.domain.userInfo.dto;

import Mefo.server.domain.allergyDrug.dto.AllergyDrugResponse;
import Mefo.server.domain.allergyDrug.entity.AllergyDrug;
import Mefo.server.domain.allergyEtc.dto.AllergyEtcResponse;
import Mefo.server.domain.allergyEtc.entity.AllergyEtc;
import Mefo.server.domain.disease.dto.DiseaseResponse;
import Mefo.server.domain.disease.entity.Disease;
import Mefo.server.domain.userAllergyDrug.entity.UserAllergyDrug;
import Mefo.server.domain.userAllergyEtc.entity.UserAllergyEtc;
import Mefo.server.domain.userDisease.entity.UserDisease;
import Mefo.server.domain.userInfo.entity.UserDrink;
import Mefo.server.domain.userInfo.entity.UserInfo;
import Mefo.server.domain.userInfo.entity.UserSex;
import Mefo.server.domain.userInfo.entity.UserSmoke;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class UserInfoResponse {
    private String name;
    private String birth;
    private String userSex;
    private float height;
    private float weight;
    private String userSmoke;
    private String userDrink;
    private boolean allergy;
    private List<AllergyDrugResponse> allergyDrugList;
    private List<AllergyEtcResponse> allergyEtcList;
    private List<DiseaseResponse> diseaseList;

    public static UserInfoResponse from(UserInfo userInfo){
        return UserInfoResponse.builder()
                .name(userInfo.getName())
                .birth(userInfo.getBirth())
                .userSex(userInfo.getUserSex().getSex())
                .height(userInfo.getHeight())
                .weight(userInfo.getWeight())
                .userSmoke(userInfo.getUserSmoke().getSmoke())
                .userDrink(userInfo.getUserDrink().getDrinkTimes())
                .allergy(userInfo.isAllergy())
                .allergyDrugList(AllergyDrugResponse.from(userInfo.getUserAllergyDrugList().stream()
                        .map(UserAllergyDrug::getAllergyDrug)
                        .collect(Collectors.toList())))
                .allergyEtcList(AllergyEtcResponse.from(userInfo.getUserAllergyEtcList().stream()
                        .map(UserAllergyEtc::getAllergyEtc)
                        .collect(Collectors.toList())))
                .diseaseList(DiseaseResponse.from(userInfo.getUserDiseaseList().stream()
                        .map(UserDisease::getDisease)
                        .collect(Collectors.toList())))
                .build();
    }
}
