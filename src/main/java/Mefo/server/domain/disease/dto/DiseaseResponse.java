package Mefo.server.domain.disease.dto;

import Mefo.server.domain.disease.entity.Disease;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class DiseaseResponse {
    private Long id;
    private String disease;
    public static List<DiseaseResponse> from(List<Disease> diseaseList){
        List<DiseaseResponse> diseaseResponseList = new ArrayList<>();
        for(Disease disease: diseaseList){
            DiseaseResponse diseaseResponse = DiseaseResponse.builder()
                    .id(disease.getId())
                    .disease(disease.getName())
                    .build();
            diseaseResponseList.add(diseaseResponse);
        }
        return diseaseResponseList;
    }
}
