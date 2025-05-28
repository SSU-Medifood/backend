package Mefo.server.domain.allergyEtc.controller;

import Mefo.server.domain.allergyEtc.dto.AllergyEtcResponse;
import Mefo.server.domain.allergyEtc.entity.AllergyEtc;
import Mefo.server.domain.allergyEtc.repository.AllergyEtcRepository;
import Mefo.server.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name="약물외 알레르기")
public class AllergyEtcController {
    private final AllergyEtcRepository allergyEtcRepository;
    //약물외 목록 불러오기
    @GetMapping({"/login/allergyEtc/show", "/allergyEtc/show"})
    @Operation(summary = "약물외 목록 불러오기")
    public ApiResponse<List<AllergyEtcResponse>> getAllergyEtc(){
        List<AllergyEtc> allergyEtcList = allergyEtcRepository.findAll();
        return new ApiResponse<>(200, AllergyEtcResponse.from(allergyEtcList));
    }
}
