package Mefo.server.domain.allergyDrug.dto;

import Mefo.server.domain.allergyDrug.entity.AllergyDrug;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class AllergyDrugResponse {
    private Long id;
    private String allergyDrug;

    public static List<AllergyDrugResponse> from(List<AllergyDrug> allergyDrugList){
        List<AllergyDrugResponse> allergyDrugResponseList = new ArrayList<>();
        for(AllergyDrug allergyDrug: allergyDrugList){
            AllergyDrugResponse allergyDrugResponse = AllergyDrugResponse.builder()
                    .id(allergyDrug.getId())
                    .allergyDrug(allergyDrug.getName())
                    .build();
            allergyDrugResponseList.add(allergyDrugResponse);
        }
        return allergyDrugResponseList;
    }
}
