package Mefo.server.domain.allergyEtc.dto;

import Mefo.server.domain.allergyEtc.entity.AllergyEtc;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class AllergyEtcResponse {
    private Long id;
    private String allergyEtc;
    public static List<AllergyEtcResponse> from(List<AllergyEtc> allergyEtcList){
        List<AllergyEtcResponse> allergyEtcResponseList = new ArrayList<>();
        for(AllergyEtc allergyEtc: allergyEtcList){
            AllergyEtcResponse allergyEtcResponse = AllergyEtcResponse.builder()
                    .id(allergyEtc.getId())
                    .allergyEtc(allergyEtc.getName())
                    .build();
            allergyEtcResponseList.add(allergyEtcResponse);
        }
        return allergyEtcResponseList;
    }
}
