package Mefo.server.domain.allergyDrug.controller;

import Mefo.server.domain.allergyDrug.dto.AllergyDrugResponse;
import Mefo.server.domain.allergyDrug.entity.AllergyDrug;
import Mefo.server.domain.allergyDrug.repository.AllergyDrugRepository;
import Mefo.server.domain.allergyDrug.service.AllergyDrugService;
import Mefo.server.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/allergyDrug")
@RequiredArgsConstructor
@Tag(name="약물 알레르기")
public class AllergyDrugController {

    private final AllergyDrugRepository allergyDrugRepository;
    //약물 목록 불러오기
    @GetMapping({"/login/allergyDrug/show", "/allergyDrug/show"})
    @Operation(summary = "약물 목록 불러오기")
    public ApiResponse<List<AllergyDrugResponse>> getAllergyDrug(){
        List<AllergyDrug> allergyDrugList = allergyDrugRepository.findAll();
        return new ApiResponse<>(200, AllergyDrugResponse.from(allergyDrugList));
    }
}
